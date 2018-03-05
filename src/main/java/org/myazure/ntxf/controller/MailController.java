package org.myazure.ntxf.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.myazure.ntxf.service.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class MailController {
	private static final Logger LOG = LoggerFactory
			.getLogger(MailController.class);
	@Autowired
	MailSenderService mailSenderService;

	@Autowired
	private AppConfiguration appConfiguration;

	@Autowired
	private StringRedisTemplate redisTemplate;

	// private RestTemplate restTemplate;
	//

	public MailController() {

	}

	public void sendLogMail(String subject, List<String> logs)
			throws MessagingException {
		StringBuffer context = new StringBuffer();
		for (String string : logs) {
			context.append(string).append("\n");
		}
		context.append("                               ").append("\n");
		context.append("                               ").append("\n");
		context.append("===知邮件请勿回复===").append("\n");
		// mailSenderService.sendAlertMail(subject, context.toString());
		//
	}

}
