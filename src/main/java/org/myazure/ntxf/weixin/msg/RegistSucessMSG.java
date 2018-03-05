package org.myazure.ntxf.weixin.msg;

import java.util.LinkedHashMap;

import org.myazure.ntxf.controller.UserController;
import org.myazure.ntxf.service.OwnerDatasService;
import org.myazure.ntxf.service.WarningDatasService;
import org.springframework.beans.factory.annotation.Autowired;

import weixin.popular.api.MessageAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 上午1:39:23
 */

public class RegistSucessMSG {
	public final static String ID = "C5nQkhqeJpbVdYA5ZRUOlUBxBT6K4ukeNfgQRPnuj_g";
	@Autowired
	UserController userController;

	@Autowired
	private WarningDatasService warningDatasService;
	@Autowired
	private OwnerDatasService ownerDatasService;

	private User user;
	private String openId;

	public RegistSucessMSG(User user, String openId) {
		this.user = UserAPI.userInfo(TokenManager.getDefaultToken(),
				user.getOpenid());
		this.openId = openId;
	}

	public void sentMsg() {
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openId);
		templateMessage.setUrl("http://www.myazure.org");
		templateMessage.setTemplate_id(RegistSucessMSG.ID);
		LinkedHashMap<String, TemplateMessageItem> msgData;
		msgData = new LinkedHashMap<String, TemplateMessageItem>();

		msgData.put(
				"first",
				new TemplateMessageItem(ownerDatasService
						.getCompanyNameByAdminunit(UserController
								.getCompId(user))
						+ "注册", "#ff0000"));
		msgData.put("keyword1",
				new TemplateMessageItem(user.getNickname_emoji(), "#ff0000"));
		msgData.put("keyword2",
				new TemplateMessageItem(user.getSubscribe_time() + "",
						"#ff0000"));
		msgData.put(
				"keyword3",
				new TemplateMessageItem(ownerDatasService
						.getCompanyNameByAdminunit(UserController
								.getCompId(user))
						+ "" + UserController.getTagNames(user),
						"#ff0000"));
		msgData.put("remark", new TemplateMessageItem("用户注册成功~！！！", "#ff0000"));
		templateMessage.setData(msgData);
		MessageAPI.messageTemplateSend(TokenManager.getDefaultToken(),
				templateMessage);
	}

}
