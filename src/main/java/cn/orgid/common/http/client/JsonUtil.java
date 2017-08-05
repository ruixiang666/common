package cn.orgid.common.http.client;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	private JsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz){
		
		T t = JSON.parseObject(json, clazz);
		return t;
		
	}

	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	}
}
