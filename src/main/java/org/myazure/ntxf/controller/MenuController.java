package org.myazure.ntxf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.Menu;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.support.TokenManager;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2017年12月28日 下午12:50:30
 */

@Controller
public class MenuController {
	private static  MenuController menuController;
	private static final Logger LOG = LoggerFactory
			.getLogger(MenuController.class);


	private static Menu menu = null;

	public MenuController() {

	}

	@PostConstruct
	public void init() {
		menuController=this;
//		setMenu();
	}
	
	
	public static void getMenu() {
		menu = MenuAPI.menuGet(TokenManager.getDefaultToken());
		LOG.debug("MENU>>>>>>");
		LOG.debug(JSON.toJSONString(menu));
		LOG.debug("<<<<<<MENU");
		if (!(menu.isSuccess() || menu.getErrcode() == "0" || menu.getErrmsg()
				.length() < 5)) {
			LOG.debug("Mwnu is empty!");
			menu = new Menu();
			MenuButtons menuButtons = new MenuButtons();
			Button[] button = new Button[3];
			button[0] = new Button();
			button[1] = new Button();
			button[2] = new Button();
			button[0].setName("消防资讯");
			button[1].setName("苏州消防");
			button[2].setName("我的消防");
			button[0].setKey("info");
			button[1].setKey("szinfo");
			button[2].setKey("myinfo");
			button[0].setType("click");
			button[1].setType("click");
			button[2].setType("click");
			List<Button> subButton1 = new ArrayList<Button>();
			List<Button> subButton2 = new ArrayList<Button>();
			List<Button> subButton3 = new ArrayList<Button>();
			Button subbutton11 = new Button();
			Button subbutton12 = new Button();
			Button subbutton13 = new Button();
			Button subbutton14 = new Button();
			Button subbutton15 = new Button();
			subbutton11.setName("每日一课");
			subbutton12.setName("安全提示");
			subbutton13.setName("火情传递");
			subbutton14.setName("专项整治");
			subbutton15.setName("消防常识");
			subbutton11.setUrl("http://weixin.119.gov.cn");
			subbutton12.setUrl("http://weixin.119.gov.cn");
			subbutton13.setUrl("http://weixin.119.gov.cn");
			subbutton14.setUrl("http://weixin.119.gov.cn");
			subbutton15.setUrl("http://weixin.119.gov.cn");
			subbutton11.setKey("11");
			subbutton12.setKey("12");
			subbutton13.setKey("13");
			subbutton14.setKey("14");
			subbutton15.setKey("15");
			subbutton11.setType("view");
			subbutton12.setType("view");
			subbutton13.setType("view");
			subbutton14.setType("view");
			subbutton15.setType("view");
			subButton1.add(subbutton11);
			subButton1.add(subbutton12);
			subButton1.add(subbutton13);
			subButton1.add(subbutton14);
			subButton1.add(subbutton15);
			Button subbutton21 = new Button();
			Button subbutton22 = new Button();
			Button subbutton23 = new Button();
			Button subbutton24 = new Button();
			Button subbutton25 = new Button();
			subbutton21.setName("消防接入");
			subbutton22.setName("管辖列表");
			subbutton23.setName("消防事件");
			subbutton24.setName("职务调整");
			subbutton25.setName("消防解绑");
			subbutton21.setUrl("http://weixin.119.gov.cn");
			subbutton22.setUrl("http://weixin.119.gov.cn");
			subbutton23.setUrl("http://weixin.119.gov.cn");
			subbutton24.setUrl("http://weixin.119.gov.cn");
			subbutton25.setUrl("http://weixin.119.gov.cn");
			subbutton21.setKey("21");
			subbutton22.setKey("22");
			subbutton23.setKey("23");
			subbutton24.setKey("24");
			subbutton25.setKey("25");
			subbutton21.setType("view");
			subbutton22.setType("view");
			subbutton23.setType("view");
			subbutton24.setType("view");
			subbutton25.setType("view");
			subButton2.add(subbutton21);
			subButton2.add(subbutton22);
			subButton2.add(subbutton23);
			subButton2.add(subbutton24);
			subButton2.add(subbutton25);
			Button subbutton31 = new Button();
			Button subbutton32 = new Button();
			Button subbutton33 = new Button();
			Button subbutton34 = new Button();
			Button subbutton35 = new Button();
			subbutton31.setName("单位负责");
			subbutton32.setName("单位法人");
			subbutton33.setName("单位值班");
			subbutton34.setName("平台监控");
			subbutton35.setName("注册登记");
			subbutton31.setUrl("http://weixin.119.gov.cn");
			subbutton32.setUrl("http://weixin.119.gov.cn");
			subbutton33.setUrl("http://weixin.119.gov.cn");
			subbutton34.setUrl("http://weixin.119.gov.cn");
			subbutton35.setUrl("http://weixin.119.gov.cn");
			subbutton31.setKey("31");
			subbutton32.setKey("32");
			subbutton33.setKey("33");
			subbutton34.setKey("34");
			subbutton35.setKey("35");
			subbutton31.setType("view");
			subbutton32.setType("view");
			subbutton33.setType("view");
			subbutton34.setType("view");
			subbutton35.setType("click");
			subButton3.add(subbutton31);
			subButton3.add(subbutton32);
			subButton3.add(subbutton33);
			subButton3.add(subbutton34);
			subButton3.add(subbutton35);
			button[0].setSub_button(subButton1);
			button[1].setSub_button(subButton2);
			button[2].setSub_button(subButton3);
			menuButtons.setButton(button);
			menuButtons.setMenuid("main");
			MenuAPI.menuCreate(TokenManager.getDefaultToken(), menuButtons);
		} else {
			MenuButtons menuButtons = menu.getMenu();
			LOG.debug("menuButtons>>>>");
			LOG.debug("           >>>>Menuid>>>>" + menuButtons.getMenuid());
			LOG.debug("           >>>>Buttons>>>>"
					+ menuButtons.getButton().length);
			for (int i = 0; i < menuButtons.getButton().length; i++) {
				LOG.debug("           >>>>Buttons>>>>" + i + " Key>>>>"
						+ menuButtons.getButton()[i].getKey());
				LOG.debug("           >>>>Buttons>>>>" + i + " Appid>>>>"
						+ menuButtons.getButton()[i].getAppid());
				LOG.debug("           >>>>Buttons>>>>" + i + " Name>>>>"
						+ menuButtons.getButton()[i].getName());
				LOG.debug("           >>>>Buttons>>>>" + i + " Type>>>>"
						+ menuButtons.getButton()[i].getType());
				LOG.debug("           >>>>Buttons>>>>" + i + " Sub_button>>>>"
						+ menuButtons.getButton()[i].getSub_button().size());
				for (int j = 0; j < menuButtons.getButton()[i].getSub_button()
						.size(); j++) {
					LOG.debug(">>>>Buttons>>>>"
							+ i
							+ " Sub_button>>>>"
							+ j
							+ " Key>>>>"
							+ menuButtons.getButton()[i].getSub_button().get(j)
									.getKey());
					LOG.debug(">>>>Buttons>>>>"
							+ i
							+ " Sub_button>>>>"
							+ j
							+ " Appid>>>>"
							+ menuButtons.getButton()[i].getSub_button().get(j)
									.getAppid());
					LOG.debug(">>>>Buttons>>>>"
							+ i
							+ " Sub_button>>>>"
							+ j
							+ " Name>>>>"
							+ menuButtons.getButton()[i].getSub_button().get(j)
									.getName());
					LOG.debug(">>>>Buttons>>>>"
							+ i
							+ " Sub_button>>>>"
							+ j
							+ " Type>>>>"
							+ menuButtons.getButton()[i].getSub_button().get(j)
									.getType());
					LOG.debug(">>>>Buttons>>>>"
							+ i
							+ " Sub_button>>>>"
							+ j
							+ " Sub_button>>>>"
							+ menuButtons.getButton()[i].getSub_button().get(j)
									.getSub_button().size());
				}
			}

		}

	}

	public static void setMenu() {
		menu = new Menu();
		MenuButtons menuButtons = new MenuButtons();
		Button[] button = new Button[3];
		button[0] = new Button();
		button[1] = new Button();
		button[2] = new Button();
		button[0].setName("消防资讯");
		button[1].setName("苏州消防");
		button[2].setName("我的消防");
		button[0].setKey("info");
		button[1].setKey("szinfo");
		button[2].setKey("myinfo");
		button[0].setType("click");
		button[1].setType("click");
		button[2].setType("click");
		List<Button> subButton1 = new ArrayList<Button>();
		List<Button> subButton2 = new ArrayList<Button>();
		List<Button> subButton3 = new ArrayList<Button>();
		Button subbutton11 = new Button();
		Button subbutton12 = new Button();
		Button subbutton13 = new Button();
		Button subbutton14 = new Button();
		Button subbutton15 = new Button();
		subbutton11.setName("每日一课");
		subbutton12.setName("安全提示");
		subbutton13.setName("火情传递");
		subbutton14.setName("专项整治");
		subbutton15.setName("消防常识");
		subbutton11.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton12.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton13.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton14.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton15.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton11.setKey("11");
		subbutton12.setKey("12");
		subbutton13.setKey("13");
		subbutton14.setKey("14");
		subbutton15.setKey("15");
		subbutton11.setType("view");
		subbutton12.setType("view");
		subbutton13.setType("view");
		subbutton14.setType("view");
		subbutton15.setType("view");
		subButton1.add(subbutton11);
		subButton1.add(subbutton12);
		subButton1.add(subbutton13);
		subButton1.add(subbutton14);
		subButton1.add(subbutton15);
		Button subbutton21 = new Button();
		Button subbutton22 = new Button();
		Button subbutton23 = new Button();
		Button subbutton24 = new Button();
		Button subbutton25 = new Button();
		subbutton21.setName("消防接入");
		subbutton22.setName("管辖列表");
		subbutton23.setName("消防事件");
		subbutton24.setName("职务调整");
		subbutton25.setName("消防解绑");
		subbutton21.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton22.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton23.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton24.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton25.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton21.setKey("21");
		subbutton22.setKey("22");
		subbutton23.setKey("23");
		subbutton24.setKey("24");
		subbutton25.setKey("25");
		subbutton21.setType("view");
		subbutton22.setType("view");
		subbutton23.setType("view");
		subbutton24.setType("view");
		subbutton25.setType("view");
		subButton2.add(subbutton21);
		subButton2.add(subbutton22);
		subButton2.add(subbutton23);
		subButton2.add(subbutton24);
		subButton2.add(subbutton25);
		Button subbutton31 = new Button();
		Button subbutton32 = new Button();
		Button subbutton33 = new Button();
		Button subbutton34 = new Button();
		Button subbutton35 = new Button();
		subbutton31.setName("扫描二维码");
		subbutton32.setName("误报处理");
		subbutton33.setName("通报上级");
		subbutton34.setName("值班签到");
		subbutton35.setName("注册登记");
		// subbutton31.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton32.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton33.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton34.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton35.setUrl("http://weixin.119.gov.cn/index.php");
		subbutton31.setKey("31");
		subbutton32.setKey("32");
		subbutton33.setKey("33");
		subbutton34.setKey("34");
		subbutton35.setKey("35");
		subbutton31.setType("scancode_push");
		subbutton32.setType("click");
		subbutton33.setType("click");
		subbutton34.setType("click");
		subbutton35.setType("click");
		subButton3.add(subbutton31);
		subButton3.add(subbutton32);
		subButton3.add(subbutton33);
		subButton3.add(subbutton34);
		subButton3.add(subbutton35);
		button[0].setSub_button(subButton1);
		button[1].setSub_button(subButton2);
		button[2].setSub_button(subButton3);
		menuButtons.setButton(button);
		menuButtons.setMenuid("main");
		MenuAPI.menuDelete(TokenManager.getDefaultToken());
		MenuAPI.menuCreate(TokenManager.getDefaultToken(), menuButtons);
	}
}
