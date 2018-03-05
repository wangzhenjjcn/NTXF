package org.myazure.ntxf.schedule;

import org.myazure.ntxf.controller.MPController;
import org.myazure.ntxf.controller.MailController;
import org.myazure.ntxf.controller.MenuController;
import org.myazure.ntxf.controller.MsgController;
import org.myazure.ntxf.controller.SDControllor;
import org.myazure.ntxf.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleMananger {
	private static final Logger LOG = LoggerFactory
			.getLogger(ScheduleMananger.class);

	@Autowired
	MailController mailController;
	@Autowired
	MenuController menuController;
	@Autowired
	MPController mPController;
	@Autowired
	UserController userController;
	@Autowired
	MsgController msgController;
	@Autowired
	SDControllor sDControllor;
	

	public ScheduleMananger() {
	}

	@Scheduled(cron = "0/1 * *  * * ? ")
	protected void lisennerScanner() {
		sDControllor.checkWarningData();
		sDControllor.checkAlertData();
	}

	@Scheduled(cron = "0/30 0/2 *  * * ? ")
	protected void checker() {
		// mPController.checkTimeOutAlert();
	}

	@Scheduled(cron = "0 0 0/1  * * ? ")
	protected void scheduleTask() {
		UserController.scheduleTask();
		MsgController.scheduleTask();
		// menuController.getMenu();
		// menuController.setMenu();
	}

}
