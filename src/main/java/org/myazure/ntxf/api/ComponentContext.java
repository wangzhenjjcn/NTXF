package org.myazure.ntxf.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author WangZhen <wangzhenjjcn@gmail.com>
 *
 */
@Component("componentContext")
public class ComponentContext {

	private String compAppId;

	private String compAppSecret;

	private String aesToken;

	private String aesKey;

	@Autowired
	public ComponentContext(@Value("${weixin.compAppId}") String compAppId,
			@Value("${weixin.compAppSecret}") String compAppSecret,
			@Value("${encode.key}") String aesKey,
			@Value("${encode.token}") String aesToken) {
		this.compAppId = compAppId;
		this.compAppSecret = compAppSecret;
		this.aesKey = aesKey;
		this.aesToken = aesToken;
	}

	public String getCompAppId() {
		return compAppId;
	}

	public String getCompAppSecret() {
		return compAppSecret;
	}

	public String getAesKey() {
		return aesKey;
	}

	public String getAesToken() {
		return aesToken;
	}

}
