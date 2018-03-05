package org.myazure.ntxf.service;

import javax.mail.MessagingException;

public interface MailSenderService {

	public void sendMailBySMTP(String subject, String context, String recivers,
			String sender) throws MessagingException;

	public void sendMail(String subject, String context, String recivers)
			throws MessagingException;

	public void sendMail(String host, String username, String password,
			String subject, String context, String recivers, String sender)
			throws MessagingException;

}
