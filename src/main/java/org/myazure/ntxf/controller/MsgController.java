package org.myazure.ntxf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.myazure.ntxf.entity.WechatServiceMsgs;
import org.myazure.ntxf.service.MyazureDataService;
import org.myazure.ntxf.service.OwnerDatasService;
import org.myazure.ntxf.service.WarningDatasService;
import org.myazure.ntxf.weixin.msg.RegistSucessMSG;
import org.myazure.utils.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import weixin.popular.api.MessageAPI;
import weixin.popular.bean.user.User;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.TokenManager;

import com.alibaba.fastjson.JSON;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@Controller
public class MsgController {
	private static final Logger LOG = LoggerFactory
			.getLogger(MsgController.class);

	@Autowired
	public MsgController(AppConfiguration appConfiguration,
			UserController userController) {
		this.appConfiguration = appConfiguration;
		this.userController = userController;
	}

	private static MsgController msgController;
	private static List<User> adminList = new ArrayList<User>();
	private static List<User> registNoticeUser = new ArrayList<User>();
	private AppConfiguration appConfiguration;
	private UserController userController;
	@Autowired
	private WarningDatasService warningDatasService;
	@Autowired
	private OwnerDatasService ownerDatasService;
	@Autowired
	private MyazureDataService myazureDataService;

	@PostConstruct
	public void init() {
		msgController = this;
		msgController.myazureDataService = this.myazureDataService;
		msgController.warningDatasService = this.warningDatasService;
		msgController.ownerDatasService = this.ownerDatasService;
		adminList = refreshUsersFromDatabase(UserController.PLATFORM_MONITOR);
		registNoticeUser = refreshUsersFromDatabase(UserController.FLLOW_CERTIFIER);
	}

	public static void scheduleTask() {
		adminList = refreshUsersFromDatabase(UserController.PLATFORM_MONITOR);
		registNoticeUser = refreshUsersFromDatabase(UserController.FLLOW_CERTIFIER);
	}

	private static List<User> refreshUsersFromDatabase(Integer userTag) {
		String userOpenIds = msgController.myazureDataService.getIDValue(
				WechatServiceMsgs.TAG_USERS_KEY_BY_TAGID, userTag);
		List<User> users = new ArrayList<User>();
		if (userOpenIds == null) {
			users = new ArrayList<User>();
		} else {
			users = new ArrayList<User>();
			for (String userOpenId : userOpenIds.split(",")) {
				users.add(UserController.getWechatUser(userOpenId));
			}
		}
		return users;
	}

	public static void sentStringMsg(String from, String to, String msg,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		if (msg == null) {
			return;
		}
		if (msg.isEmpty()) {
			return;
		}
		XMLMessage xmlTextMessage = new XMLTextMessage(to, from, msg);
		xmlTextMessage.outputStreamWrite(response.getOutputStream(),
				wxBizMsgCrypt);
	}

	public static void sentStringMsg(EventMessage eventMessage, String msg,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		if (msg == null) {
			return;
		}
		if (msg.isEmpty()) {
			return;
		}
		XMLMessage xmlTextMessage = new XMLTextMessage(
				eventMessage.getFromUserName(), eventMessage.getToUserName(),
				msg);
		xmlTextMessage.outputStreamWrite(response.getOutputStream(),
				wxBizMsgCrypt);
	}

	public static void sentWelcomeMsg(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.refreshUserFromWechat(eventMessage
				.getFromUserName());
		LOG.debug(JSON.toJSONString(currentUser));
		String msg = "欢迎" + currentUser.getNickname()+ "关注:\n苏州市消防设施联网检测平台\n宁泰服务平台";
		MsgController.sentStringMsg(eventMessage, msg, response, wxBizMsgCrypt);
	}

	public static void sentRequireTobeRegiestMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		String msg = "尊敬的"
				+ currentUser.getNickname()
				+ ":\n"
				+ "您尚未进行【绑定操作】\n"
				+ "您的消息我们已经推送给客服处理\n若需要绑定企业\n请依次点击菜单【 我的消防】->【注册登记】开始绑定流程\n谢谢合作！";
		MsgController.sentStringMsg(eventMessage, msg, response, wxBizMsgCrypt);
	}

	public static void sentServiceMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		String msg = "尊敬的" + currentUser.getNickname() + ":\n"
				+ "您的消息我们已经推送给客服处理\n谢谢！";
		MsgController.sentStringMsg(eventMessage, msg, response, wxBizMsgCrypt);
	}

	public static void sentLinkingMsgRequireCode(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		String msg = "尊敬的"
				+ currentUser.getNickname()
				+ ":\n您正在进行【绑定操作】\n请回复你的【绑定代码】或者【站点代码】\n【允许地理位置访问】以确保可以辅助处理警情\n若在菜单界面请点击【左下角键盘】按钮进入输入界面\n谢谢合作！";
		MsgController.sentStringMsg(eventMessage, msg, response, wxBizMsgCrypt);
	}

	public static void sentHaveCompanyCode(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n").append(
				"你已经绑定公司【" + UserController.getCompName(currentUser) + "】\n");
		textMsg.append("的" + UserController.getTagNames(currentUser));
		textMsg.append("\n" + "您若需要重新绑定，请【重新关注宁泰消防】以重新绑定！谢谢合作！");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLinkingMsgRequireDuty(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n")
				.append("你已经绑定公司【" + UserController.getCompName(currentUser)
						+ "】\n").append("您【尚未绑定职责】若要绑定请按照以下提示回复【相应数字】！\n");
		textMsg.append("【1】企业法人");
		textMsg.append("\n");
		textMsg.append("【2】企业消防负责人");
		textMsg.append("\n");
		textMsg.append("【3】企业消防管理员");
		textMsg.append("\n");
		textMsg.append("【4】企业消控值班员");
		textMsg.append("\n");
		textMsg.append("若在【菜单界面】请点击左下角【键盘按钮】进入【输入界面】，谢谢合作！");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLinkSuccessCompMSG(EventMessage eventMessage,
			HttpServletResponse response, String compName,
			WXBizMsgCrypt wxBizMsgCrypt) throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n"
				+ "您正在进行【绑定操作】\n");
		textMsg.append("绑定企业：【" + compName + "】\n");
		textMsg.append("绑定状态：【成功】\n");
		textMsg.append(
				"你已经绑定公司【" + compName+ "】\n")
				.append("您【尚未绑定职责】若要绑定请按照以下提示回复【相应数字】！\n");
		textMsg.append("【1】企业法人");
		textMsg.append("\n");
		textMsg.append("【2】企业消防负责人");
		textMsg.append("\n");
		textMsg.append("【3】企业消防管理员");
		textMsg.append("\n");
		textMsg.append("【4】企业消控值班员");
		textMsg.append("\n");
		textMsg.append("若在【菜单界面】请点击左下角【键盘按钮】进入【输入界面】，谢谢合作！");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLinkFaildCompMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n"
				+ "您正在进行【绑定操作】\n");
		textMsg.append("绑定企业：【未知】\n");
		textMsg.append("绑定状态：【失败】\n");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLingkingDutySucessMSG(EventMessage eventMessage,
			HttpServletResponse response, String compname, String duty,
			WXBizMsgCrypt wxBizMsgCrypt) throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n"
				+ "您正在进行【绑定操作】\n");
		textMsg.append("绑定企业：【" + compname + "】\n");
		textMsg.append("绑定职责：【" + duty + "】！\n");
		textMsg.append("绑定状态：【成功】！\n");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLingkingDutyFaildMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n"
				+ "您正在进行【绑定操作】\n");
		textMsg.append("绑定职责：【不存在】！\n");
		textMsg.append("绑定状态：【失败】！\n");
		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentLingkingAlreadyMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt)
			throws IOException {
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("尊敬的" + currentUser.getNickname() + ":\n").append(
				"你已经绑定公司【" + UserController.getCompName(currentUser)
						+ "】不可更改\n");
		textMsg.append("若需要重新绑定企业\n请【取消关注】并【重新关注】【宁泰消防】以开始绑定流程\n谢谢合作！");
		textMsg.append("请回复和你对应身份类型后的数字：\n企业法人【1】\n企业消防负责人【2】\n企业消防管理员【3】\n企业消控值班员【4】\n若在菜单界面请点击左下角键盘按钮进入输入界面，谢谢合作！");

		MsgController.sentStringMsg(eventMessage, textMsg.toString(), response,
				wxBizMsgCrypt);
	}

	public static void sentRegistNoticeMSG(User currentUser) {
		for (User registAdmin : registNoticeUser) {
			TemplateMessage templateMessage = new TemplateMessage();
			templateMessage.setTouser(registAdmin.getOpenid());
			// templateMessage.setUrl("http://www.myazure.org");
			templateMessage.setTemplate_id(RegistSucessMSG.ID);
			LinkedHashMap<String, TemplateMessageItem> msgData;
			msgData = new LinkedHashMap<String, TemplateMessageItem>();
			msgData.put(
					"first",
					new TemplateMessageItem(UserController
							.getCompName(currentUser) + "\n用户注册成为"+UserController.getTagNames(currentUser), "#ff0000"));
			msgData.put("keyword1",
					new TemplateMessageItem(currentUser.getNickname(),
							"#ff0000"));
			msgData.put(
					"keyword2",
					new TemplateMessageItem(S.formatWXTime(currentUser
							.getSubscribe_time() + "")
							+ "", "#ff0000"));
			msgData.put(
					"keyword3",
					new TemplateMessageItem("微信自主注册",
							"#ff0000"));
			msgData.put("remark", new TemplateMessageItem(" ",
					"#ff0000"));
			templateMessage.setData(msgData);
			Long idString = MessageAPI.messageTemplateSend(
					TokenManager.getDefaultToken(), templateMessage).getMsgid();
			LOG.debug("NewMSG_SEND:" + registAdmin.getOpenid() + " ID:"
					+ idString);
		}
	}

	public static void setOprateSucessMSG(EventMessage eventMessage,
			HttpServletResponse response, WXBizMsgCrypt wxBizMsgCrypt) throws IOException {
		String msg = "操作成功";
		MsgController.sentStringMsg(eventMessage, msg, response, wxBizMsgCrypt);
		
	}

}
