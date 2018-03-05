package org.myazure.ntxf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.myazure.ntxf.domain.AlertData;
import org.myazure.ntxf.domain.OwnerDatas;
import org.myazure.ntxf.domain.WarningDatas;
import org.myazure.ntxf.entity.WechatServiceMsgs;
import org.myazure.ntxf.service.AlertDataService;
import org.myazure.ntxf.service.MyazureDataService;
import org.myazure.ntxf.service.OwnerDatasService;
import org.myazure.ntxf.service.WarningDatasService;
import org.myazure.ntxf.weixin.msg.AlertMSG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;

import weixin.popular.bean.user.User;

@Controller
public class SDControllor {
	private static SDControllor sDControllor;
	private static boolean alertWorking = false;
	private static boolean warningWorking = false;

	private static final Logger LOG = LoggerFactory
			.getLogger(SDControllor.class);
	@Autowired
	private WarningDatasService warningDatasService;
	@Autowired
	private OwnerDatasService ownerDatasService;
	@Autowired
	private MyazureDataService myazureDataService;
	@Autowired
	private AlertDataService alertDataService;

	public SDControllor() {
	}

	@PostConstruct
	public void init() {
		sDControllor = this;
		sDControllor.myazureDataService = this.myazureDataService;

	}

	public void checkWarningData() {
		if (warningWorking) {
			return;
		}
		warningWorking = true;
		if (myazureDataService.getValue(WechatServiceMsgs.LAST_SENT_WARNING) == null) {
			myazureDataService.save(WechatServiceMsgs.LAST_SENT_WARNING,
					warningDatasService.getLastMsgId() + "");
		}
		while (warningDatasService.getLastMsgId() > Integer
				.valueOf(myazureDataService
						.getValue(WechatServiceMsgs.LAST_SENT_WARNING))) {
			WarningDatas warningDatas = warningDatasService
					.findOneById(Integer.valueOf(myazureDataService
							.getValue(WechatServiceMsgs.LAST_SENT_WARNING)) + 1);
			if (warningDatas == null) {
				myazureDataService
						.save(WechatServiceMsgs.LAST_SENT_WARNING,
								(Integer.valueOf(myazureDataService
										.getValue(WechatServiceMsgs.LAST_SENT_WARNING)) + 1)
										+ "");
				continue;
			}
			OwnerDatas ownerDatas = ownerDatasService
					.findOneByOwnerCode(warningDatas.getOwnerCode());
			warningDatas.setOwnerDatas(ownerDatas);
			List<String> sentUserList = new ArrayList<String>();
			Map<String, AlertMSG> alertMsgs = new HashMap<String, AlertMSG>();

			if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(warningDatas
							.getOwnerCode())
							+ "_"
							+ UserController.CONTROL_ROOM_ATTENDANT) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(warningDatas
								.getOwnerCode())
								+ "_"
								+ UserController.CONTROL_ROOM_ATTENDANT).split(
						",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								warningDatas));
					}
				}
			} else if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(warningDatas
							.getOwnerCode())
							+ "_"
							+ UserController.FIRE_FIGHTER) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(warningDatas
								.getOwnerCode())
								+ "_"
								+ UserController.FIRE_FIGHTER).split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								warningDatas));
					}
				}
			} else if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(warningDatas
							.getOwnerCode()) + "_" + UserController.FIRE_CHIEF) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(warningDatas
								.getOwnerCode())
								+ "_"
								+ UserController.FIRE_CHIEF).split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								warningDatas));
					}
				}
			} else if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(warningDatas
							.getOwnerCode()) + "_" + UserController.LEGAL) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(warningDatas
								.getOwnerCode()) + "_" + UserController.LEGAL)
						.split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								warningDatas));
					}
				}
			}
			if (sentUserList.size() < 1) {
				LOG.debug("NoUser2SentAlert:["
						+ warningDatas.getOwnerDatas().getOwnerName() + "],ID:"
						+ warningDatas.getId() + " Code:"
						+ warningDatas.getOwnerDatas().getAdminunit());
				if (myazureDataService.getIDValue(
						WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID,
						UserController.PLATFORM_MONITOR) != null) {
					String[] monitors = myazureDataService.getIDValue(
							WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID,
							UserController.PLATFORM_MONITOR).split(",");
					if (monitors.length > 0) {
						for (String user2sent : monitors) {
							sentUserList.add(user2sent);
							alertMsgs.put(user2sent, new AlertMSG(user2sent,
									warningDatas));
						}
					}
				}
				if (sentUserList.size() < 1) {
					LOG.debug("NoMonitorUser2SentAlert:["
							+ warningDatas.getOwnerDatas().getOwnerName()
							+ "],ID:" + warningDatas.getId() + " Code:"
							+ warningDatas.getOwnerDatas().getAdminunit());
				}
			}
			for (String sentUserOpenId : alertMsgs.keySet()) {
				String msgIdString = "";
				try {
					msgIdString = alertMsgs.get(sentUserOpenId).sendMSG();
				} catch (Exception e) {
					LOG.debug(e.getMessage());
					if (UserController.refreshUserFromWechat(sentUserOpenId)
							.getSubscribe() == 0) {
						LOG.debug("UserNOT SUbscribe!!!!!!!!!!!!!!!!!!");
						continue;
					} else {
						LOG.debug("EEEEEEEEEEEEEEEEEEEEEEEEEEEEER!!!!!!!!!!!!!!!!!!");
						continue;
					}
				}
				myazureDataService.save(
						WechatServiceMsgs.TEMPLATE_SEND_JOB_FINISH_PRE
								+ warningDatas.getOwnerDatas().getAdminunit()
								+ "_" + warningDatas.getId() + "_"
								+ msgIdString, sentUserOpenId);
				LOG.debug("Curret To:"
						+ UserController.getWechatUser(sentUserOpenId)
								.getNickname() + " errID:"
						+ warningDatas.getId() + "msgId:" + msgIdString
						+ " COMP:"
						+ warningDatas.getOwnerDatas().getAdminunit()
						+ " NAME:"
						+ warningDatas.getOwnerDatas().getOwnerName());

			}
			myazureDataService.save(
					WechatServiceMsgs.TEMPLATE_SEND_JOB_FINISH_ID.replace("ID",
							warningDatas.getId() + ""),
					System.currentTimeMillis() + "");
			myazureDataService.save(
					WechatServiceMsgs.COMP_LAST_ALERT_TIME_ID.replace("ID",
							warningDatas.getOwnerDatas().getAdminunit()),
					System.currentTimeMillis() + "");

			myazureDataService.save(WechatServiceMsgs.LAST_SENT_WARNING,
					warningDatas.getId() + "");

		}
		warningWorking = false;
	}

	public void wanrningChecked(User user) {
		if (UserController.havaComp(user)) {
			LOG.debug(UserController.getCompName(user)
					+ "ReadAllTOBeDeleted ReadBy" + user.getNickname());
			// myazureDataService.deleteAllMyazureDatasByKeyStartWith(keyPreString)
		} else {
			return;
		}
	}

	public void checkAlertData() {
		if (alertWorking) {
			return;
		}
		alertWorking = true;
		if (myazureDataService.getValue(WechatServiceMsgs.LAST_SENT_ALERT) == null) {
			myazureDataService.save(WechatServiceMsgs.LAST_SENT_ALERT,
					alertDataService.getLastMsgId() + "");
		}
		while (alertDataService.getLastMsgId() > Integer
				.valueOf(myazureDataService
						.getValue(WechatServiceMsgs.LAST_SENT_ALERT))) {
			AlertData alertData = alertDataService.findOneById(Integer
					.valueOf(myazureDataService
							.getValue(WechatServiceMsgs.LAST_SENT_ALERT)) + 1);
			if (alertData == null) {
				myazureDataService
						.save(WechatServiceMsgs.LAST_SENT_ALERT,
								(Integer.valueOf(myazureDataService
										.getValue(WechatServiceMsgs.LAST_SENT_ALERT)) + 1)
										+ "");
				continue;
			}
			if (alertData.getReserve() != null) {
				myazureDataService.save(WechatServiceMsgs.LAST_SENT_ALERT,
						alertData.getId() + "");
				
				continue;
			} 
			OwnerDatas ownerDatas = ownerDatasService
					.findOneByOwnerCode(alertData.getOwnerCode());
			alertData.setOwnerDatas(ownerDatas);
			List<String> sentUserList = new ArrayList<String>();
			Map<String, AlertMSG> alertMsgs = new HashMap<String, AlertMSG>();

			if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(alertData
							.getOwnerCode())
							+ "_"
							+ UserController.CONTROL_ROOM_ATTENDANT) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(alertData
								.getOwnerCode())
								+ "_"
								+ UserController.CONTROL_ROOM_ATTENDANT).split(
						",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								alertData));
					}
				}
			} 
 if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(alertData
							.getOwnerCode())
							+ "_"
							+ UserController.FIRE_FIGHTER) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(alertData
								.getOwnerCode())
								+ "_"
								+ UserController.FIRE_FIGHTER).split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								alertData));
					}
				}
			} 
 if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(alertData
							.getOwnerCode()) + "_" + UserController.FIRE_CHIEF) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(alertData
								.getOwnerCode())
								+ "_"
								+ UserController.FIRE_CHIEF).split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								alertData));
					}
				}
			} 
 if (myazureDataService.getIDValue(
					WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
					ownerDatasService.getAdminunitByOwnerCode(alertData
							.getOwnerCode()) + "_" + UserController.LEGAL) != null) {

				String[] companyUsers = myazureDataService.getIDValue(
						WechatServiceMsgs.COMP_USERS_KEY_BY_COMPID_AND_TAGID,
						ownerDatasService.getAdminunitByOwnerCode(alertData
								.getOwnerCode()) + "_" + UserController.LEGAL)
						.split(",");
				if (companyUsers.length > 0) {
					for (String user2sent : companyUsers) {
						sentUserList.add(user2sent);
						alertMsgs.put(user2sent, new AlertMSG(user2sent,
								alertData));
					}
				}
			}
			if (sentUserList.size() < 1) {
				LOG.debug("NoUser2SentAlertalrtsmsg:["
						+ alertData.getOwnerDatas().getOwnerName() + "],ID:"
						+ alertData.getId() + " Code:"
						+ alertData.getOwnerDatas().getAdminunit());
				if (myazureDataService.getIDValue(
						WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID,
						UserController.PLATFORM_MONITOR) != null) {
					String[] monitors = myazureDataService.getIDValue(
							WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID,
							UserController.PLATFORM_MONITOR).split(",");
					if (monitors.length > 0) {
						for (String user2sent : monitors) {
							sentUserList.add(user2sent);
							alertMsgs.put(user2sent, new AlertMSG(user2sent,
									alertData));
						}
					}
				}
				if (sentUserList.size() < 1) {
					LOG.debug("NoMonitorUser2SentAlertalrtsmsg:["
							+ alertData.getOwnerDatas().getOwnerName()
							+ "],ID:" + alertData.getId() + " Code:"
							+ alertData.getOwnerDatas().getAdminunit());
				continue;
				}
			}
			for (String sentUserOpenId : alertMsgs.keySet()) {
				String msgIdString = "";
				try {
					msgIdString = alertMsgs.get(sentUserOpenId).sendMSG();
				} catch (Exception e) {
					LOG.debug(sentUserOpenId);
					LOG.debug(JSON.toJSONString(alertMsgs.get(sentUserOpenId)));
					LOG.debug(e.getMessage());
					if (UserController.refreshUserFromWechat(sentUserOpenId)
							.getSubscribe() == 0) {
						LOG.debug("UserNOT SUbscribe!!!!!!alrtsmsg!!!!!!!!!!!");
						continue;
					} else {
						LOG.debug("EEEEEEEEEEalrtsmsgEEEEEEEEEEEEEEEEEEER!!!!!!!!!!!!!!!!!!");
						continue;
					}
				}
				myazureDataService.save(
						WechatServiceMsgs.TEMPLATE_SEND_JOB_FINISH_PRE
								+ alertData.getOwnerDatas().getAdminunit()
								+ "_" + alertData.getId() + "_" + msgIdString,
						sentUserOpenId);
				LOG.debug("Curret To:"
						+ UserController.getWechatUser(sentUserOpenId)
								.getNickname() + " errID:" + alertData.getId()
						+ "msgId:" + msgIdString + " COMP:"
						+ alertData.getOwnerDatas().getAdminunit() + " NAME:"
						+ alertData.getOwnerDatas().getOwnerName());

			}
			myazureDataService.save(
					WechatServiceMsgs.TEMPLATE_SEND_JOB_FINISH_ID.replace("ID",
							alertData.getId() + ""), System.currentTimeMillis()
							+ "");
			myazureDataService.save(WechatServiceMsgs.COMP_LAST_ALERT_TIME_ID
					.replace("ID", alertData.getOwnerDatas().getAdminunit()),
					System.currentTimeMillis() + "");

			myazureDataService.save(WechatServiceMsgs.LAST_SENT_ALERT,
					alertData.getId() + "");

		}
		alertWorking = false;
	}

}
