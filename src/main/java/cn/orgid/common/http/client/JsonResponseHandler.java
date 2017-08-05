package cn.orgid.common.http.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class JsonResponseHandler {
	
	private static final String OK = "0";

	public static final String ErrorCodeKey = "errcode";

	public static final String ErrorMsgKey = "errmsg";

	private static Map<String, ResponseHandler<?>> map = new HashMap<String, ResponseHandler<?>>();

	@SuppressWarnings("unchecked")
	public static <T> ResponseHandler<T> createResponseHandler(
			final Class<T> clazz) {

		if (map.containsKey(clazz.getName())) {
			return (ResponseHandler<T>) map.get(clazz.getName());
		} else {
			ResponseHandler<T> responseHandler = new ResponseHandler<T>() {
				public T handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						String str = EntityUtils.toString(entity);
						JSONObject obj = (JSONObject) JSON.parse(str);
						//String errcode = obj.getString(ErrorCodeKey);
//						if (errcode != null && !errcode.isEmpty()&&!errcode.equals(OK)) {
//							throw new RuntimeException(
//									obj.getString(ErrorMsgKey));
//						}
						return JsonUtil
								.parseObject(str, clazz);
					} else {
						HttpEntity entity = response.getEntity();
						String str = EntityUtils.toString(entity);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			map.put(clazz.getName(), responseHandler);
			return responseHandler;
		}
	}

}
