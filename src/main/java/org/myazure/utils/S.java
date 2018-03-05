package org.myazure.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myazure.ntxf.response.StatusResponse;

import com.alibaba.fastjson.JSON;
import com.mysql.fabric.xmlrpc.base.Data;

public final class S {
	public static final String UTF8 = "utf-8";
	public static final String GBK = "gbk";
	public static final String GBK2312 = "gbk2312";
	public static final String ISO = "iso-8859-1";

	private S() {

	}

	// ä¸­å½äºº UTF8------UTF8>ISO
	// 涓浗浜� UTF8------UTF8>GBK
	// 中国�? GBK-------GBK>UTF8
	/**
	 * 
	 * @param utf8String
	 * @return
	 */
	public static final String utf8ToGBK(String utf8String) {
		try {
			return new String(
					new String(utf8String.getBytes(UTF8), ISO).getBytes(ISO),
					GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String("");
	}

	/**
	 * 
	 * @param gbkString
	 * @return
	 */
	public static final String gbkToUtf8(String gbkString) {
		try {
			return new String(
					new String(gbkString.getBytes(GBK), ISO).getBytes(ISO),
					UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String("");
	}

	public static final String bytes2IntNums(byte[] datas, String split) {
		StringBuffer value = new StringBuffer();
		for (int i = 0; i < datas.length; i += 2) {
			byte high = datas[i];
			byte low = datas[i + 1];
			value.append(String.valueOf(((high << 8)) | (low & 0xff))).append(
					split);
		}
		value.deleteCharAt(datas.length - 1);
		return value.toString();
	}

	public static final BigDecimal toBigDecimal(String data) {
		BigDecimal bd = new BigDecimal(data);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	public static final Short toShort(String data) {
		return new Short(data);
	}

	public static void entResponse(HttpServletResponse response, Object object) {
		try {
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(object));
			response.getWriter().close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void entResponse(HttpServletResponse response, String msg) {
		try {
			StatusResponse statusResponse = new StatusResponse(msg, 0, true);
			response.setContentType("application/json");
			response.getWriter().write(JSON.toJSONString(statusResponse));
			response.getWriter().close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTimeNowStringByDateTimeFormat() {
		return F.dateTimeFormat().format(new Date());
	}

	public static String getTimeDateStringByDateTimeFormat(Long time) {
		return F.dateTimeFormat().format(new Date(time));
	}

	public static String getTodayDateStringByDateFormat() {
		return F.dayFormat().format(new Date());
	}

	public static String getDayDateStringByDateFormat(Date date) {
		return F.dayFormat().format(date);
	}

	public static String getTimeStringAfterTime(Long time) {
		String resault = ((System.currentTimeMillis() - time) / 3600000 / 24)
				+ "天" + ((System.currentTimeMillis() - time) / 3600000) % 24
				+ "小时" + ((System.currentTimeMillis() - time) / 60000) % 60
				+ "分钟" + ((System.currentTimeMillis() - time) / 1000) % 60
				+ "秒";
		return resault;

	}

	public static String getTimeStringBetweenTimes(Long current, Long history) {
		String resault = ((current - history) / 3600000 / 24) + "天"
				+ ((current - history) / 3600000) % 24 + "小时"
				+ ((current - history) / 60000) % 60 + "分钟"
				+ ((current - history) / 1000) % 60 + "秒";
		return resault;

	}

	public static String getTimeStringBetweenTimesOnlyOneUnit(Long current,
			Long history) {
		String resault = "";
		if (current - history < 60000) {
			resault = ((current - history) / 1000) % 60 + "秒";
		} else if (current - history < 3600000) {
			resault = ((current - history) / 60000) % 60 + "分钟";
		} else if (current - history < 86400000) {
			resault = ((current - history) / 3600000) % 24 + "小时";
		} else if (current - history >= 86400000) {
			resault = ((current - history) / 3600000 / 24) + "天";
		}
		return resault;
	}

	public static String getTimeStringBetweenTimesOnlyTwoUnit(Long current,
			Long history) {
		StringBuffer resault = new StringBuffer();
		if (current - history < 60000) {
			resault.append(((current - history) / 1000) % 60 + "秒");
		} else if (current - history < 3600000) {
			resault.append(((current - history) / 60000) % 60 + "分种");
			resault.append(((current - history) / 1000) % 60 + "秒");
		} else if (current - history < 86400000) {
			resault.append(((current - history) / 3600000) % 24 + "小时");
			resault.append(((current - history) / 60000) % 60 + "分");
		} else if (current - history >= 86400000) {
			resault.append(((current - history) / 3600000 / 24) + "天");
			resault.append(((current - history) / 3600000) % 24 + "小时");
		}
		return resault.toString();
	}

	public static boolean isNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean outputStreamWrite(OutputStream outputStream,
			String text) {
		try {
			outputStream.write(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getMTimeStringBetweenTimes(Long current, Long history) {
		String resault = ((current - history) / 60000) + "分钟";
		return resault;

	}

	public static String getSmartTimeStringBetweenTimes(Long current,
			Long history) {
		return getTimeStringBetweenTimesOnlyOneUnit(current, history);
	}

	public static String buildUrl(String url, Map<String, String> params) {
		if (null != params && params.size() > 0) {
			for (String key : params.keySet()) {
				url = url.replace("{" + key + "}", params.get(key));
			}
		}
		return url;
	}

	public static String getStrFromInputSteam(InputStream in)
			throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = bf.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	public static String getRequestJsonString(HttpServletRequest request)
			throws IOException {
		String submitMehtod = request.getMethod();
		// GET
		if (submitMehtod.equals("GET")) {
			return new String(request.getQueryString().getBytes("iso-8859-1"),
					"utf-8").replaceAll("%22", "\"");
			// POST
		} else {
			return getRequestPostStr(request);
		}
	}

	public static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	public static String getRequestPostStr(HttpServletRequest request,
			String charEncoding) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		if (charEncoding == null) {
			return new String(buffer, "UTF-8");
		}
		return new String(buffer, charEncoding);
	}

	public static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	public static String formatWXTime(String createTime) {
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));

	}
}
