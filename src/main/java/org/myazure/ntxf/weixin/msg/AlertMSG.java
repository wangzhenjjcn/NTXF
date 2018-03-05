package org.myazure.ntxf.weixin.msg;

import java.util.LinkedHashMap;

import org.myazure.ntxf.controller.UserController;
import org.myazure.ntxf.domain.AlertData;
import org.myazure.ntxf.domain.WarningDatas;
import org.myazure.ntxf.service.OwnerDatasService;
import org.myazure.ntxf.service.WarningDatasService;
import org.myazure.utils.S;
import org.springframework.beans.factory.annotation.Autowired;

import weixin.popular.api.MessageAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 下午6:53:44
 */

public class AlertMSG {
	public final static String ID = "bqSnC7_qGZzzqpRmhuTCwrG9xCaBbhyyPfqBwRKFIck";
	private String openId;
	private User user;
	@Autowired
	private UserController userController;
	private WarningDatas warningDatas;
	private AlertData alertData;
	private boolean isWarningData = true;
	@Autowired
	private WarningDatasService warningDatasService;
	@Autowired
	private OwnerDatasService ownerDatasService;

	public AlertMSG(String openId, WarningDatas warningDatas) {
		this.openId = openId;
		this.user = UserController.getWechatUser(openId);
		this.warningDatas = warningDatas;
	}

	public AlertMSG(User user, WarningDatas warningDatas) {
		this.user = user;
		this.openId = user.getOpenid();
		this.warningDatas = warningDatas;
	}

	public AlertMSG(User user, AlertData alertData) {
		this.alertData=alertData;
		this.isWarningData=false;
		this.user = user;
		this.openId = user.getOpenid();

	}

	public AlertMSG(String openId, AlertData alertData) {
		this.user = UserController.getWechatUser(openId);
		this.openId = user.getOpenid();
		this.alertData=alertData;
		this.isWarningData=false;
	}

	public AlertMSG() {

	}

	public String sendMSG() {
		// // {{first.DATA}}
		// 报警类型：{{keyword1.DATA}}
		// 报警时间：{{keyword2.DATA}}
		// 报警地点：{{keyword3.DATA}}
		// 报警节点：{{keyword4.DATA}}
		// 报警分类：{{keyword5.DATA}}
		// {{remark.DATA}}
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTouser(openId);
		// templateMessage.setUrl("http://www.myazure.org");
		templateMessage.setTemplate_id(AlertMSG.ID);
		LinkedHashMap<String, TemplateMessageItem> msgData;
		msgData = new LinkedHashMap<String, TemplateMessageItem>();
		msgData.put(
				"keyword1",
				new TemplateMessageItem(((isWarningData?warningDatas.getAlarmType():8 )+ "")
						.replace("1", "火警").replace("2", "故障")
						.replace("3", "启动").replace("4", "反馈")
						.replace("5", "监管").replace("6", "复位")
						.replace("7", "屏蔽").replace("8", "手动传输")
						+ " " + "ID：" + (isWarningData?warningDatas.getId():alertData.getId()), "#ff0000"));
		msgData.put("keyword2", new TemplateMessageItem((isWarningData?warningDatas.getTime():alertData.getTime())
				.getTime().toLocaleString(), "#ff0000"));
		msgData.put(
				"keyword3",
				new TemplateMessageItem(S.ToDBC(((isWarningData?warningDatas.getWhereDesc():alertData.getWhereDesc())==null?"":(isWarningData?warningDatas.getWhereDesc():alertData.getWhereDesc()))
						.trim().replace(" ", "")), "#ff0000"));
		msgData.put("keyword4", new TemplateMessageItem(((isWarningData?warningDatas
				.getSourceDesc():alertData.getSourceDesc())==null?" ":(isWarningData?warningDatas
						.getSourceDesc():alertData.getSourceDesc())).trim(), "#ff0000"));
		msgData.put("keyword5",
				new TemplateMessageItem((isWarningData?warningDatas.getDeviceDesc():alertData.getDeviceDesc()) + " "
						+ (isWarningData?warningDatas.getPartCode():" "), "#ff0000"));
		msgData.put("first",
				new TemplateMessageItem("" + UserController.getTagNames(user)
						+ user.getNickname() + "请及时查看报警信息", "#ff0000"));
		msgData.put("remark", new TemplateMessageItem((isWarningData?warningDatas
				.getOwnerDatas():alertData.getOwnerDatas()).getOwnerName() + "\n10分钟内未处理将上报消防责任人处理",
				"#ff0000"));
		// msgData.put("remark", new TemplateMessageItem("若10分钟内未处理将上报处理",
		// "#ff0000"));
		templateMessage.setData(msgData);
		long idString = MessageAPI.messageTemplateSend(
				TokenManager.getDefaultToken(), templateMessage).getMsgid();
		return "" + idString;

	}

}
