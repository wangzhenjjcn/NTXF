package org.myazure.ntxf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.myazure.ntxf.domain.MyazureData;
import org.myazure.ntxf.entity.WechatServiceMsgs;
import org.myazure.ntxf.service.MyazureDataService;
import org.myazure.ntxf.service.OwnerDatasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;

import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.Group;
import weixin.popular.bean.user.Group.GroupData;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2017年12月29日 下午2:19:43
 */
@Controller
public class UserController {
	private static UserController userController;
	@Autowired
	private MyazureDataService myazureDataService;
	@Autowired
	private OwnerDatasService ownerDatasService;

	private static final Logger LOG = LoggerFactory
			.getLogger(UserController.class);
	public static boolean userTagInited = false;
	public final static Integer ALLTAGS = -10086;
	public final static Integer UNGROUPED = 0;
	public final static Integer BLACK_LIST = 1;
	public final static Integer STAR = 2;
	public final static Integer LEGAL = 100;
	public final static Integer FIRE_CHIEF = 101;
	public final static Integer FIRE_FIGHTER = 102;
	public final static Integer CONTROL_ROOM_ATTENDANT = 103;
	public final static Integer FLLOW_CERTIFIER = 104;
	public final static Integer PLATFORM_MONITOR = 105;
	public final static Integer TO_BE_REGISTED = 106;
	public final static Integer REGISTERED = 107;
	public final static Integer DELETED_1 = 108;
	public final static Integer UNREGISTERED = 109;
	public final static Integer TIME_OUT_NO_RESPONSE_PERSON = 110;
	public final static Integer DELETED_2 = 111;
	public final static Integer MANY_TIMES_NO_RESPOND = 112;
	public final static Integer MAINTENANCE_UNIT = 113;

	public UserController() {

	}

	@PostConstruct
	public void init() {
		userController = this;
		userController.myazureDataService = this.myazureDataService;
		userController.ownerDatasService = this.ownerDatasService;
		refreshAllUsersFromWechat();
		refreshTagNameFromWechat();
		updateCompAlertUsers();
	}

	public static void scheduleTask() {
		refreshAllUsersFromWechat();
		refreshTagNameFromWechat();
		updateCompAlertUsers();
		
	}

	public void setNewRegistWxUserTag(User user) {
		addUserIntoTag(user, UNREGISTERED);
	}

	public static boolean isTaged(User user) {
		if (user == null) {
			return false;
		}
		if (user.getTagid_list() == null) {
			return false;
		}
		if (user.getTagid_list().length <= 0) {
			return false;
		}
		if (user.getTagid_list().length > 0) {
			return true;
		}
		return false;
	}

	public static boolean isInTag(User user, Integer tagId) {
		if (user == null) {
			return false;
		}
		if (user.getTagid_list() == null) {
			return false;
		}
		if (user.getTagid_list().length <= 0) {
			return false;
		}
		if (user.getTagid_list().length > 0) {
			for (Integer integer : user.getTagid_list()) {
				if (integer == tagId) {
					return true;
				}
			}
		}
		return false;
	}

	public static User addUserIntoTag(User user, Integer tagId) {
		String[] openids = new String[1];
		openids[0] = user.getOpenid();
		if (isInTag(user, tagId)) {
			return user;
		} else {
			UserAPI.tagsMembersBatchtagging(TokenManager.getDefaultToken(),
					tagId, openids);
		}
		return refreshUserFromWechat(user); 
	}

	public static User removeUserFromTag(User user, Integer tagId) {
		String[] openids = new String[1];
		openids[0] = user.getOpenid();
		if (isInTag(user, tagId)) {
			UserAPI.tagsMembersBatchuntagging(TokenManager.getDefaultToken(),
					tagId, openids);
		}
		return refreshUserFromWechat(user);
	}

	public static void removeAllUserTags(User user) {
		String[] openids = new String[1];
		openids[0] = user.getOpenid();
		for (Integer tagsId : user.getTagid_list()) {
			removeUserFromTag(user, tagsId);
		}
	}

	public static User moveUserFromTagToTag(User user, Integer fromTagId,
			Integer toTagId) {
		removeUserFromTag(user, fromTagId);
		addUserIntoTag(user, toTagId);
		return refreshUserFromWechat(user);
	}

 

	public static boolean havaComp(User user) {
		if (user==null) {
			return false;
		}
		String userRemark = user.getRemark();
		if (userRemark == null) {
			return false;
		}
		if (userRemark.isEmpty() || userRemark.contains("未")) {
			return false;
		}
		if (!userRemark.contains("[")) {
			return false;
		}
		if (!userRemark.contains("]")) {
			return false;
		}
		if (userRemark.substring(userRemark.indexOf("[") + 1,
				userRemark.indexOf("]")).isEmpty()) {
			return false;
		}
		if (userRemark.substring(userRemark.indexOf("[") + 1,
				userRemark.indexOf("]")) == null) {
			return false;
		}
		return true;
	}

	public static String getCompId(User user) {
		return havaComp(user) ? user.getRemark().substring(
				user.getRemark().indexOf("[") + 1,
				user.getRemark().indexOf("]")) : null;
	}

	public static String getCompName(User user) {
		return havaComp(user) ? userController.ownerDatasService
				.getCompanyNameByAdminunit(getCompId(user)) : "";
	}

	public static User addCompanyId(User user, String compId) {
		if (compId == null) {
			return user;
		}
		if (havaComp(user)) {
			removeCompanyId(user);
		}
		String userMarkString = user.getRemark();
		StringBuffer userMarkInfo = new StringBuffer();
		userMarkInfo.append("[").append(compId).append("]");
		if (userMarkString == null || userMarkString.isEmpty()) {

		} else if (userMarkString.contains("[") && userMarkString.contains("]")) {
			userMarkString.replace("[", "");
			userMarkString.replace("]", "");
			userMarkInfo.append(",").append(userMarkString);
		} else {
			userMarkInfo.append(",").append(userMarkString);
		}
		UserAPI.userInfoUpdateremark(TokenManager.getDefaultToken(),
				user.getOpenid(), userMarkInfo.toString());
		return refreshUserFromWechat(user);
	}

	public static User removeCompanyId(User user) {
		String userMarkString = user.getRemark();
		if (userMarkString.split(",").length > 1) {
			UserAPI.userInfoUpdateremark(TokenManager.getDefaultToken(),
					user.getOpenid(), userMarkString.split(",")[1]);
		} else {
			UserAPI.userInfoUpdateremark(TokenManager.getDefaultToken(),
					user.getOpenid(), "");
		}
		return refreshUserFromWechat(user);
	}

	public static User getWechatUser(String openId) {
		String userData = userController.myazureDataService
				.getValue(WechatServiceMsgs.USER_INFO_ID.replace("ID", openId));
		if (userData == null || userData.isEmpty()) {
			refreshUserFromWechat(openId);
			userData = userController.myazureDataService.getValue(openId);
			if (userData == null || userData.isEmpty()) {
				return null;
			}
		}
		return JSON.parseObject(JSON.parseObject(userData, String.class),
				User.class);
	}

	public static String getTagNames(User user) {
		String splitString = "、";
		if (user.getTagid_list().length == 1) {
			return getTagName(user.getTagid_list()[0]);
		}
		if (user.getTagid_list().length > 1) {
			StringBuffer tagString = new StringBuffer();
			for (Integer userTagId : user.getTagid_list()) {
				tagString.append(getTagName(userTagId)).append(splitString);

			}
			tagString.deleteCharAt(tagString.length() - 1);
			return tagString.toString();
		}
		return "未知";
	}

	public static String getTagName(Integer id) {
		String chineseName = userController.myazureDataService
				.getValue(WechatServiceMsgs.USER_TAG_CHINESE_ID.replace("ID",
						"" + id));
		if (chineseName == null) {
			refreshTagNameFromWechat();
		} else {
			return chineseName;
		}
		return userController.myazureDataService
				.getValue(WechatServiceMsgs.USER_TAG_CHINESE_ID.replace("ID",
						"" + id)) == null ? ""
				: userController.myazureDataService
						.getValue(WechatServiceMsgs.USER_TAG_CHINESE_ID
								.replace("ID", "" + id));
	}

	public static void refreshTagNameFromWechat() {
		Group allGroup = UserAPI.groupsGet(TokenManager.getDefaultToken());
		for (GroupData groupData : allGroup.getGroups()) {
			userController.myazureDataService.save(
					WechatServiceMsgs.USER_TAG_CHINESE_ID.replace("ID",
							groupData.getId()), groupData.getName());
		}
	}

	public static void refreshAllUsersFromWechat() {
		userController.myazureDataService.deleteAllMyazureDatasByKeyStartWith(WechatServiceMsgs.USER_INFO_ID.replace("ID",""));
		FollowResult followResult = UserAPI.userGet(
				TokenManager.getDefaultToken(), null);
		LOG.debug("USERSSS---" + JSON.toJSONString(followResult));
		List<User> user = new ArrayList<User>();
		if (followResult == null) {
			return;
		}
		if (followResult.getTotal() < 1) {
			return;
		}
		if (followResult.getData() == null) {
			LOG.debug("ALERT RESAULT DATA NULLLLLLLLL");
			return;
		}
		String[] userStrings = followResult.getData().getOpenid();
		while (user.size() < followResult.getTotal()) {
			if (user.size() < 1
					&& followResult.getData().getOpenid().length > 0) {
				for (String openId : userStrings) {
					refreshUserFromWechat(openId);
					user.add(getWechatUser(openId));
					LOG.debug("OPENID:" + openId + "---"
							+ JSON.toJSONString(getWechatUser(openId)));
				}
			}
			followResult = UserAPI.userGet(TokenManager.getDefaultToken(),
					followResult.getNext_openid());
			if (followResult.getData() == null) {
				LOG.debug("USERS NULL:" + JSON.toJSONString(followResult));
				break;
			}
			if (followResult.getData().getOpenid() == null) {
				LOG.debug("USERS NULL:" + JSON.toJSONString(followResult));
				break;
			}
			if (followResult.getData().getOpenid().length < 1) {
				LOG.debug("USERS NULL:" + JSON.toJSONString(followResult));
				break;
			}
			userStrings = followResult.getData().getOpenid();
			for (String openId : userStrings) {
				refreshUserFromWechat(openId);
				user.add(getWechatUser(openId));
				LOG.debug("OPENID:" + openId + "---"
						+ JSON.toJSONString(getWechatUser(openId)));
			}
		}
	}

	public static User refreshUserFromWechat(String openId) {
		User user = UserAPI.userInfo(TokenManager.getDefaultToken(), openId);
		if (user.isSuccess() && (user != null) && (user.getOpenid() != null)) {
			userController.myazureDataService.save(
					WechatServiceMsgs.USER_INFO_ID.replace("ID", openId),
					JSON.toJSONString(user));
		}
		return user;
	}

	public static User refreshUserFromWechat(User user) {
		User userUpdate = UserAPI.userInfo(TokenManager.getDefaultToken(),
				user.getOpenid());
		if (userUpdate.isSuccess() && (userUpdate != null)
				&& (userUpdate.getOpenid() != null)) {
			userController.myazureDataService.save(
					WechatServiceMsgs.USER_INFO_ID.replace("ID",
							userUpdate.getOpenid()),
					JSON.toJSONString(userUpdate));
		}
		return userUpdate;
	}

	public static void updateCompAlertUsers() {
		userController.myazureDataService
				.deleteAllMyazureDatasByKeyStartWith(WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID
						.replace("ID", ""));
		userController.myazureDataService
				.deleteAllMyazureDatasByKeyStartWith(WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID
						.replace("ID", ""));
		List<MyazureData> datas = userController.myazureDataService
				.findMyazureDataByMkeyStartingWith(WechatServiceMsgs.USER_INFO_ID
						.replace("ID", ""));
		LOG.debug("USERINFO,SIZE:" + datas.size());
		List<User> users = new ArrayList<User>();
		Map<String, String> compUsers = new HashMap<String, String>();
		for (MyazureData myazureData : datas) {
			LOG.debug("myazureData:" + JSON.toJSONString(myazureData));
			User user = JSON.parseObject(
					JSON.parseObject(myazureData.getMvalue(), String.class),
					User.class);
			users.add(user);
			if (havaComp(user)) {
				if (user.getTagid_list().length > 0) {
					for (Integer tagId : user.getTagid_list()) {
						if (!compUsers.containsKey(getCompId(user) + "_"
								+ tagId)) {
							compUsers.put(getCompId(user) + "_" + tagId,
									user.getOpenid());
						} else {
							StringBuffer userStringBuffer = new StringBuffer();
							userStringBuffer
									.append(compUsers.get(getCompId(user) + "_"
											+ tagId)).append(",")
									.append(user.getOpenid());
							compUsers.put(getCompId(user) + "_" + tagId,
									userStringBuffer.toString());
						}
					}
				}
			}
			if (user.getTagid_list().length < 1) {
				continue;
			} else {
				for (Integer tagId : user.getTagid_list()) {
					if (!compUsers.containsKey("" + tagId)) {
						compUsers.put("" + tagId, user.getOpenid());
					} else {
						StringBuffer userStringBuffer = new StringBuffer();
						userStringBuffer.append(compUsers.get("" + tagId))
								.append(",").append(user.getOpenid());
						compUsers.put("" + tagId, userStringBuffer.toString());
					}
				}
			}
		}

		for (String compId : compUsers.keySet()) {
			LOG.debug("compId:" + JSON.toJSONString(compId));
			if (compUsers.get(compId) == null
					|| compUsers.get(compId).isEmpty()) {
				continue;
			} else {
				if (compId.contains("_")) {
					userController.myazureDataService
							.save(WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID
									.replace("ID", compId), compUsers
									.get(compId));
				} else {
					userController.myazureDataService.save(
							WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID.replace(
									"ID", compId), compUsers.get(compId));
				}
			}
		}
	}

	public static String getUsersStringByCompId(String compId) {
		List<MyazureData> users = userController.myazureDataService
				.findMyazureDataByMkeyStartingWith(WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID
						.replace("ID", compId + ""));
		StringBuffer usersString = new StringBuffer();
		for (MyazureData myazureData : users) {
			usersString.append(myazureData.getMvalue()).append(",");
		}
		usersString.deleteCharAt(usersString.length());
		return usersString.toString();
	}

	public static String getUsersStringByTagId(String tagId) {
		return userController.myazureDataService
				.getValue(WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID.replace(
						"ID", tagId + ""));
	}

	public static void refreshLastUserContactTimeInfo(EventMessage eventMessage) {
		userController.myazureDataService.save(new MyazureData(
				WechatServiceMsgs.USER_LAST_REQUEST_TIME_KEYPRE
						+ eventMessage.getFromUserName(), eventMessage
						.getCreateTime() + ""));
	}

	public static void refreshLastUserContactTimeInfo(User user, String time) {
		userController.myazureDataService.save(new MyazureData(
				WechatServiceMsgs.USER_LAST_REQUEST_TIME_KEYPRE
						+ user.getOpenid(), time + ""));
	}

	public static boolean isToBeRegisted(User currentUser) {
		return UserController.isInTag(currentUser,
				UserController.TO_BE_REGISTED);
	}

	public static void saveUserLovationInfo(EventMessage eventMessage) {
		userController.myazureDataService.save(new MyazureData(
				WechatServiceMsgs.USER_LOCATION_KEYPRE
						+ eventMessage.getFromUserName(), eventMessage
						.getLatitude() + "," + eventMessage.getLongitude()));
	}

	public static void saveTemplateMSGSentedTimeInfo(EventMessage eventMessage) {
		userController.myazureDataService.save(new MyazureData(
				WechatServiceMsgs.TEMPLATE_SEND_JOB_FINISH_ID.replace("ID",
						eventMessage.getMsgId()), System.currentTimeMillis()
						+ ""));
	}

}
