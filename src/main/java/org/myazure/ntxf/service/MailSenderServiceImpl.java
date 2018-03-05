package org.myazure.ntxf.service;

import javax.mail.MessagingException;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailSenderServiceImpl implements MailSenderService {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private AppConfiguration appConfiguration;

	@Autowired
	public MailSenderServiceImpl(AppConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}

	@Override
	public void sendMailBySMTP(String subject, String context, String recivers,
			String sender) throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMail(String subject, String context, String recivers)
			throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMail(String host, String username, String password,
			String subject, String context, String recivers, String sender)
			throws MessagingException {
		// TODO Auto-generated method stub

	}

	// @Value("${mail.smtp.auth.username}")
	// private String mailSender;

}
