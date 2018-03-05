package org.myazure.ntxf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.myazure.ntxf.domain.MyazureData;
import org.myazure.ntxf.domain.OwnerDatas;
import org.myazure.ntxf.domain.WarningDatas;
import org.myazure.ntxf.entity.WechatServiceMsgs;
import org.myazure.ntxf.service.MyazureDataService;
import org.myazure.ntxf.service.OwnerDatasService;
import org.myazure.ntxf.service.WarningDatasService;
import org.myazure.ntxf.weixin.msg.AlertMSG;
import org.myazure.utils.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Element;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.User;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import com.alibaba.fastjson.JSON;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 
 * @author WangZhen
 *
 */

@Controller
public class MPController {
	private static final Logger LOG = LoggerFactory
			.getLogger(MPController.class);
	private AppConfiguration appConfiguration;
	private MsgController msgController;
	private UserController userController;
	private SDControllor sDControllor;
	private static ExpireKey expireKey = new DefaultExpireKey();
	@Autowired
	private WarningDatasService warningDatasService;
	@Autowired
	private OwnerDatasService ownerDatasService;
	@Autowired
	private MyazureDataService myazureDataService;

	WXBizMsgCrypt wxBizMsgCrypt = null;

	@Autowired
	public MPController(AppConfiguration appConfiguration,
			UserController userController, MsgController msgController,
			SDControllor sDControllor) {
		this.appConfiguration = appConfiguration;
		this.msgController = msgController;
		this.userController = userController;
		this.sDControllor = sDControllor;
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public void postRoot(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AesException {
		EventMessage eventMessage = getEventMessage(request, response);
		if (eventMessage == null) {
			return;
		} else if (eventMessage.getMsgType() == null) {
			return;
		}
		User currentUser = UserController.getWechatUser(eventMessage
				.getFromUserName());
		switch (eventMessage.getMsgType()) {
		case "event":
			switch (eventMessage.getEvent()) {
			case "subscribe":
				MsgController.sentWelcomeMsg(eventMessage, response,
						wxBizMsgCrypt);
				UserController.refreshLastUserContactTimeInfo(eventMessage);
				break;
			case "unsubscribe":
				if (UserController.havaComp(currentUser)) {
					LOG.debug("-用户取消关注-");
					LOG.debug("companyId:"
							+ UserController.getCompId(currentUser));
					LOG.debug("companyString:"
							+ UserController.getCompName(currentUser));
				}
				UserController.refreshUserFromWechat(eventMessage
						.getFromUserName());
				UserController.refreshLastUserContactTimeInfo(eventMessage);
				break;
			case "scan":
				UserController.refreshLastUserContactTimeInfo(eventMessage);
				break;
			case "scancode_push":
				List<Element> elements = eventMessage.getOtherElements();
				for (Element element : elements) {
					LOG.debug(JSON.toJSONString(element));
				}
				UserController.refreshLastUserContactTimeInfo(eventMessage);
				break;
			case "event":
				LOG.debug("事件消息!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
						+ JSON.toJSONString(eventMessage));
				break;
			case "CLICK":
				UserController.refreshLastUserContactTimeInfo(eventMessage);
				switch (eventMessage.getEventKey()) {
				case "31":
					MsgController.setOprateSucessMSG(
							eventMessage, response, wxBizMsgCrypt);
					return;
				case "32":
					if (UserController.havaComp(UserController.getWechatUser(eventMessage.getFromUserName()))) {
						myazureDataService.save(new MyazureData(
								WechatServiceMsgs.COMP_LAST_READ_TIME_ID.replace("ID", UserController.getCompId
										(UserController.getWechatUser(eventMessage.getFromUserName())))
										,""+System.currentTimeMillis()));
					}
					MsgController.setOprateSucessMSG(
							eventMessage, response, wxBizMsgCrypt);
					return;
				case "33":
					if (UserController.havaComp(UserController.getWechatUser(eventMessage.getFromUserName()))) {
						myazureDataService.save(new MyazureData(
								WechatServiceMsgs.COMP_LAST_READ_TIME_ID.replace("ID", UserController.getCompId
										(UserController.getWechatUser(eventMessage.getFromUserName())))
										,""+System.currentTimeMillis()));
					}
					MsgController.setOprateSucessMSG(
							eventMessage, response, wxBizMsgCrypt);
					return;
				case "34":
					if (UserController.havaComp(UserController.getWechatUser(eventMessage.getFromUserName()))) {
						myazureDataService.save(new MyazureData(
								WechatServiceMsgs.COMP_LAST_READ_TIME_ID.replace("ID", UserController.getCompId
										(UserController.getWechatUser(eventMessage.getFromUserName())))
										,""+System.currentTimeMillis()));
					}
					MsgController.setOprateSucessMSG(
							eventMessage, response, wxBizMsgCrypt);
					return;
				case "35":
					if (!UserController.havaComp(currentUser)) {
						if (currentUser.getTagid_list().length == 0) {
							currentUser = UserController.addUserIntoTag(
									currentUser, UserController.TO_BE_REGISTED);
							currentUser = UserController.removeUserFromTag(
									currentUser, UserController.UNREGISTERED);
							MsgController.sentLinkingMsgRequireCode(
									eventMessage, response, wxBizMsgCrypt);
							return;
						} else if (UserController.isInTag(currentUser,
								UserController.BLACK_LIST)) {
							return;
						} else {
							if (UserController.isInTag(currentUser,
									UserController.PLATFORM_MONITOR)) {
								currentUser = UserController.addUserIntoTag(
										currentUser,
										UserController.TO_BE_REGISTED);
								MsgController.sentLinkingMsgRequireCode(
										eventMessage, response, wxBizMsgCrypt);
								return;
							} else {
								if (UserController.isInTag(currentUser,
										UserController.TO_BE_REGISTED)) {
									MsgController.sentLinkingMsgRequireCode(
											eventMessage, response,
											wxBizMsgCrypt);
									return;
								}
							}
						}
					} else {
						if (UserController.isInTag(currentUser,
								UserController.TO_BE_REGISTED)) {
							MsgController.sentLinkingMsgRequireDuty(
									eventMessage, response, wxBizMsgCrypt);
							return;
						} else {
							MsgController.sentHaveCompanyCode(eventMessage,
									response, wxBizMsgCrypt);
							return;
						}
					}
				default:
					LOG.debug("未开发的事件KEY！类型:" + eventMessage.getEventKey());
					break;
				}
				break;
			case "LOCATION":
				LOG.debug("NewLocation!!!!!!!!!!!!!!!"
						+ UserController.getWechatUser(
								eventMessage.getFromUserName()).getNickname()
						+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				UserController.saveUserLovationInfo(eventMessage);
				if (UserController.havaComp(UserController.getWechatUser(eventMessage.getFromUserName()))) {
					myazureDataService.save(new MyazureData(
							WechatServiceMsgs.COMP_LAST_READ_TIME_ID.replace("ID", UserController.getCompId
									(UserController.getWechatUser(eventMessage.getFromUserName())))
									,""+System.currentTimeMillis()));
				}
				sDControllor.wanrningChecked(currentUser);
				break;
			case "TEMPLATESENDJOBFINISH":
				UserController.saveTemplateMSGSentedTimeInfo(eventMessage);
				break;
			default:
				LOG.debug("未开发的类型:" + eventMessage.getMsgType());
				break;
			}
			break;
		case "text":
			if (eventMessage.getContent().contains("收到不支持的消息类型，暂无法显示")) {
				MsgController.sentStringMsg(eventMessage, "你发送了:"
						+ eventMessage.getContent(), response, wxBizMsgCrypt);
				return;
				// break;
			}
			if (!UserController.havaComp(currentUser)) {
				if (UserController.isInTag(currentUser,
						UserController.TO_BE_REGISTED)) {
					if (S.isNum(eventMessage.getContent())) {
						if (Integer.valueOf(eventMessage.getContent())
								.intValue() < 10) {
							MsgController.sentRequireTobeRegiestMSG(
									eventMessage, response, wxBizMsgCrypt);
							return;
						} else {
							if (ownerDatasService.getCompanyNameByAdminunit(
									eventMessage.getContent()).isEmpty()
									|| ownerDatasService
											.getCompanyNameByAdminunit(eventMessage
													.getContent()) == "") {
								MsgController.sentLinkFaildCompMSG(
										eventMessage, response, wxBizMsgCrypt);
								return;
							} else {
								UserController.addCompanyId(currentUser,
										eventMessage.getContent());
								MsgController
										.sentLinkSuccessCompMSG(
												eventMessage,
												response,
												ownerDatasService
														.getCompanyNameByAdminunit(eventMessage
																.getContent()),
												wxBizMsgCrypt);
								return;
							}

						}
					} else {
						MsgController.sentServiceMSG(eventMessage, response,
								wxBizMsgCrypt);
						return;
					}
				} else {
					MsgController.sentServiceMSG(eventMessage, response,
							wxBizMsgCrypt);
					return;
				}
			} else {
				if (UserController.isInTag(currentUser,
						UserController.TO_BE_REGISTED)) {
					if (S.isNum(eventMessage.getContent())) {
						if (Integer.valueOf(eventMessage.getContent())
								.intValue() < 9) {
							String dutyString = "";
							LOG.debug("User:" + currentUser.getNickname()
									+ " NUM:" + eventMessage.getContent());
							switch (eventMessage.getContent()) {
							default:
								MsgController.sentLingkingDutyFaildMSG(
										eventMessage, response, wxBizMsgCrypt);
								return;
								// break;
							case "1":// 企业法人
								dutyString = "企业法人";
								currentUser = UserController.addUserIntoTag(
										currentUser, UserController.LEGAL);
								currentUser = UserController.removeUserFromTag(
										currentUser,
										UserController.TO_BE_REGISTED);
								break;
							case "2":// 企业消防负责人
								dutyString = "企业消防负责人";
								currentUser = UserController.addUserIntoTag(
										currentUser, UserController.FIRE_CHIEF);
								currentUser = UserController.removeUserFromTag(
										currentUser,
										UserController.TO_BE_REGISTED);
								break;
							case "3":// 企业消防管理员
								dutyString = "企业消防管理员";
								currentUser = UserController.addUserIntoTag(
										currentUser,
										UserController.FIRE_FIGHTER);
								currentUser = UserController.removeUserFromTag(
										currentUser,
										UserController.TO_BE_REGISTED);
								break;
							case "4":// 企业消控值班员
								dutyString = "企业消控值班员";
								currentUser = UserController.addUserIntoTag(
										currentUser,
										UserController.CONTROL_ROOM_ATTENDANT);
								currentUser = UserController.removeUserFromTag(
										currentUser,
										UserController.TO_BE_REGISTED);
								break;
							}
							MsgController.sentLingkingDutySucessMSG(
									eventMessage, response,
									UserController.getCompName(currentUser),
									dutyString, wxBizMsgCrypt);
							MsgController.sentRegistNoticeMSG(currentUser);
							currentUser = UserController.removeUserFromTag(
									currentUser, UserController.TO_BE_REGISTED);
							currentUser = UserController.removeUserFromTag(
									currentUser, UserController.UNREGISTERED);
							return;
						} else {
							MsgController.sentLingkingAlreadyMSG(eventMessage,
									response, wxBizMsgCrypt);
							return;
						}
					} else {
						MsgController.sentServiceMSG(eventMessage, response,
								wxBizMsgCrypt);
					}
				} else {
					MsgController.sentServiceMSG(eventMessage, response,
							wxBizMsgCrypt);
				}
			}
			break;
		case "voice":
			MsgController.sentServiceMSG(eventMessage, response, wxBizMsgCrypt);
			break;
		case "image":
			MsgController.sentServiceMSG(eventMessage, response, wxBizMsgCrypt);
			break;
		case "video":
			MsgController.sentServiceMSG(eventMessage, response, wxBizMsgCrypt);
			break;
		case "location":
			MsgController.sentServiceMSG(eventMessage, response, wxBizMsgCrypt);
			break;

		case "link":
			MsgController.sentServiceMSG(eventMessage, response, wxBizMsgCrypt);
			break;
		default:
			if (eventMessage.getEvent().isEmpty()) {
				LOG.debug("未开发的getEe.getEvent().isEmpty()vent类型:"
						+ eventMessage.getEvent());
			} else {
				LOG.debug("未开发的getEvent类型:" + eventMessage.getEvent());
			}
			break;
		}
		return;
	}

	private EventMessage getEventMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		EventMessage eventMessage = null;
		ServletInputStream inputStream = request.getInputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String encrypt_type = request.getParameter("encrypt_type");
		String msg_signature = request.getParameter("msg_signature");
		boolean isAes = "aes".equals(encrypt_type);
		if (isAes) {
			try {
				wxBizMsgCrypt = new WXBizMsgCrypt(
						appConfiguration.getEncodeToken(),
						appConfiguration.getEncodeKey(),
						appConfiguration.getCompAppId());
			} catch (AesException e) {
				e.printStackTrace();
			}
		}
		if (isAes && echostr != null) {
			try {
				echostr = URLDecoder.decode(echostr, "utf-8");
				String echostr_decrypt = wxBizMsgCrypt.verifyUrl(msg_signature,
						timestamp, nonce, echostr);
				writeOutput(response, echostr_decrypt);
				return null;
			} catch (AesException e) {
				e.printStackTrace();
			}
		} else if (echostr != null) {
			writeOutput(response, echostr);
			return null;
		}
		if (isAes) {
			try {
				String postData = StreamUtils.copyToString(inputStream,
						Charset.forName("utf-8"));
				String xmlData = wxBizMsgCrypt.decryptMsg(msg_signature,
						timestamp, nonce, postData);
				eventMessage = XMLConverUtil.convertToObject(
						EventMessage.class, xmlData);
			} catch (AesException e) {
				e.printStackTrace();
			}
		} else {
			if (signature == null) {
				LOG.debug("signature:is null");
			}
			LOG.debug("signature:" + signature);
			LOG.debug("timestamp:" + timestamp);
			LOG.debug("nonce:" + nonce);
			LOG.debug("signature:" + signature);
			LOG.debug("signature:" + signature);
			if (!signature.equals(SignatureUtil.generateEventMessageSignature(
					appConfiguration.getEncodeToken(), timestamp, nonce))) {
				LOG.debug("The request signature is invalid");
				response.getWriter().append("unsuccess");
				response.getWriter().flush();
				return null;
			}
			if (inputStream != null) {
				eventMessage = XMLConverUtil.convertToObject(
						EventMessage.class, inputStream);
			}
			response.getWriter().append("success");
			response.getWriter().flush();
			response.getWriter().close();
		}
		String key = eventMessage.getFromUserName() + "__"
				+ eventMessage.getToUserName() + "__" + eventMessage.getMsgId()
				+ "__" + eventMessage.getCreateTime();
		if (expireKey.exists(key)) {
			return null;
		} else {
			expireKey.add(key);
		}
		return eventMessage;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public void getRoot(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AesException {
		LOG.debug("A GET Request==================================");
		EventMessage eventMessage = getEventMessage(request, response);
		if (eventMessage == null) {
			return;
		}
		if (eventMessage.getMsgType() == null) {
			return;
		}

		LOG.debug("MMMMMMMMMMSG:" + JSON.toJSONString(eventMessage));
		return;
	}

	// 授权事件处理
	@RequestMapping(path = "/event/authorize", method = RequestMethod.POST)
	public void acceptAuthorizeEvent(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AesException {

		LOG.debug("event authorize!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		writeOutput(response, "success");
	}

	public static void sent(HttpServletResponse response, XMLMessage msg,
			WXBizMsgCrypt wxBizMsgCrypt) throws IOException {
		msg.outputStreamWrite(response.getOutputStream(), wxBizMsgCrypt);
	}

	public static void writeOutput(HttpServletResponse response, String value) {
		try {
			PrintWriter pw = response.getWriter();
			pw.write(value);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
