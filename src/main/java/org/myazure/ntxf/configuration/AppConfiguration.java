package org.myazure.ntxf.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppConfiguration {
	@Value("${server.port}")
	private String serverPort;

	@Value("${mail.smtp.auth}")
	private String mailSmtpAuth;
	@Value("${mail.host}")
	private String mailHost;
	@Value("${mail.transport.protocol}")
	private String mailTransportProtocol;
	@Value("${mail.smtp.auth.username}")
	private String mailSmtpAuthUsername;
	@Value("${mail.smtp.auth.password}")
	private String mailSmtpAuthPassword;
	@Value("${mail.alert.recievers}")
	private String mailAlertRecivers;

	@Value("${mp.refreshAccessTokenTime}")
	private Long refreshAccessTokenTime;

	@Value("${weixin.compAppId}")
	private String compAppId;

	@Value("${weixin.compAppSecret}")
	private String compAppSecret;

	@Value("${encode.token}")
	private String encodeToken;

	@Value("${encode.key}")
	private String encodeKey;

	public AppConfiguration() {

	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailTransportProtocol() {
		return mailTransportProtocol;
	}

	public void setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
	}

	public String getMailSmtpAuthUsername() {
		return mailSmtpAuthUsername;
	}

	public void setMailSmtpAuthUsername(String mailSmtpAuthUsername) {
		this.mailSmtpAuthUsername = mailSmtpAuthUsername;
	}

	public String getMailSmtpAuthPassword() {
		return mailSmtpAuthPassword;
	}

	public void setMailSmtpAuthPassword(String mailSmtpAuthPassword) {
		this.mailSmtpAuthPassword = mailSmtpAuthPassword;
	}

	public String getMailAlertRecivers() {
		return mailAlertRecivers;
	}

	public void setMailAlertRecivers(String mailAlertRecivers) {
		this.mailAlertRecivers = mailAlertRecivers;
	}

	public Long getRefreshAccessTokenTime() {
		return refreshAccessTokenTime;
	}

	public void setRefreshAccessTokenTime(Long refreshAccessTokenTime) {
		this.refreshAccessTokenTime = refreshAccessTokenTime;
	}

	public String getCompAppId() {
		return compAppId;
	}

	public void setCompAppId(String compAppId) {
		this.compAppId = compAppId;
	}

	public String getCompAppSecret() {
		return compAppSecret;
	}

	public void setCompAppSecret(String compAppSecret) {
		this.compAppSecret = compAppSecret;
	}

	public String getEncodeToken() {
		return encodeToken;
	}

	public void setEncodeToken(String encodeToken) {
		this.encodeToken = encodeToken;
	}

	public String getEncodeKey() {
		return encodeKey;
	}

	public void setEncodeKey(String encodeKey) {
		this.encodeKey = encodeKey;
	}

}
