package org.myazure.utils;

import com.alibaba.fastjson.JSON;

public class Decode {
	public static String DESDecode(String string) {
		return DES.decode(JSON.parseObject(string, String.class));
	}

	public static String DESDecode(Object object) {
		return DESDecode(JSON.parseObject(JSON.toJSONString(object),
				String.class));
	}

}
