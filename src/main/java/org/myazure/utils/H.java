package org.myazure.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H {

	private static final Logger LOG = LoggerFactory.getLogger(H.class);

	public H() {

	}

	public static HttpResponse getResponse(String URL) {
		CloseableHttpResponse httpResponse = null;
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpGet get = new HttpGet(URL);
			httpResponse = httpClient.execute(get);
			try {
				return httpResponse;
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getForMethod(String URL) throws ParseException,
			IOException {
		HttpResponse httpResponse = null;
		httpResponse = getResponse(URL);
		HttpEntity entity = httpResponse.getEntity();
		if (null != entity) {
			return EntityUtils.toString(entity);
		}
		return "";
	}

	public static String getCookie(String URL) throws ParseException,
			IOException {
		HttpResponse httpResponse = getResponse(URL);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
				&& httpResponse.containsHeader("Set-Cookie")) {
			return httpResponse.getFirstHeader("Set-Cookie").getValue();
		}
		return null;
	}

	public static HttpResponse postResponse(String URL,
			Map<String, String> header, Map<String, String> param,
			String encodeCharsetString) {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost(URL);
			for (String headerKeyString : header.keySet()) {
				post.addHeader(headerKeyString, header.get(headerKeyString));
			}
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String paramString : param.keySet()) {
				params.add(new BasicNameValuePair(paramString, param
						.get(paramString)));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,
					encodeCharsetString == null ? "UTF-8" : encodeCharsetString);
			post.setEntity(uefEntity);
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				return httpResponse;
			} finally {
				httpResponse.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static String postForMethod(String URL, Map<String, String> header,
			Map<String, String> param, String encodeCharsetString) {
		CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost(URL);
			for (String headerKeyString : header.keySet()) {
				post.addHeader(headerKeyString, header.get(headerKeyString));
			}
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String paramString : param.keySet()) {
				params.add(new BasicNameValuePair(paramString, param
						.get(paramString)));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,
					encodeCharsetString == null ? "UTF-8" : encodeCharsetString);
			post.setEntity(uefEntity);
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				String resaultString = "";
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					resaultString = EntityUtils.toString(httpResponse
							.getEntity());
				}
				return resaultString;
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "";
			} finally {
				httpResponse.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static String postForCookie(String URL, Map<String, String> header,
			Map<String, String> param, String encodeCharsetString) {
		HttpResponse httpResponse = postResponse(URL, header, param,
				encodeCharsetString);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
				&& httpResponse.containsHeader("Set-Cookie")) {
			return httpResponse.getFirstHeader("Set-Cookie").getValue();
		} else {
			return null;
		}
	}

	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client)
			throws IOException {
		if (client != null) {
			client.close();
		}
	}
}
