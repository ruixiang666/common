package cn.orgid.common.http.client;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpClientComponent {

	public static Header Header = new BasicHeader(HttpHeaders.CONTENT_TYPE,
			ContentType.create("application/x-www-form-urlencoded",
					Consts.UTF_8).toString());

	public static <T> T executePost(RequestParameters parameters, String url,
			ResponseHandler<T> responseHandler) {
		try {

			HttpUriRequest httpUriRequest = parameters.build(url);
			return LocalHttpClient.execute(httpUriRequest, responseHandler);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T execute(RequestParameters parameters, String url,
			ResponseHandler<T> responseHandler) {
		try {

			HttpUriRequest httpUriRequest = parameters.build(url);
			return LocalHttpClient.execute(httpUriRequest, responseHandler);

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class RequestParameters {

		private Map<String, String> paraMap = new HashMap<String, String>();

		public void add(String n, String v) {

			paraMap.put(n, v);

		}

		public HttpUriRequest build(String url) {

			HttpUriRequest r = null;
			RequestBuilder b = RequestBuilder.post().setHeader(Header)
					.setUri(url);

			try {
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				Set<String> ks = paraMap.keySet();
				for (String k : ks) {
					formParams.add(new BasicNameValuePair(k, paraMap.get(k)));
				}
				HttpEntity entity = new UrlEncodedFormEntity(formParams,
						"UTF-8");

				b.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			r = b.build();
			return r;

		}

	}

	public static String uploadFile(Map<String, String> paras,
			String localFileName,byte[] content, String url,MediaType type) {
		
		OkHttpClient client = new OkHttpClient();
		RequestBody fileBody = RequestBody.create(type, content);
		
		MultipartBuilder b = new MultipartBuilder();
		b.addFormDataPart("file", localFileName, fileBody);
		Set<Entry<String, String>> ps = paras.entrySet();
		for (Entry<String, String> entry : ps) {
			b.addFormDataPart(entry.getKey(), entry.getValue());
		}
		RequestBody requestBody = b.build();
		Request request = new Request.Builder().url(url).post(requestBody)
				.build();
		Response response;
		try {
			client.setConnectTimeout(250, TimeUnit.SECONDS);
			client.setReadTimeout(250, TimeUnit.SECONDS);
			client.setWriteTimeout(250, TimeUnit.SECONDS);
			response = client.newCall(request).execute();
			String jsonString = response.body().string();
			JSONObject obj = JSON.parseObject(jsonString);
			if(obj.getBooleanValue("success")){
				return obj.getString("value");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	
	public static void main(String[] args) {
		
		MediaType type=MediaType.parse("image/jpeg");
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("appId", "9441a1673a804af6a6183095ae052256");
		paras.put("appSecret", "549747c560f8421db2a2459096d66e11");
		byte[] content = new byte[10];
		String localFileName="1.jpg";
		String url="http://testdb.hi-travel.cn:8088/service/upload_file.htm";
		String s =HttpClientComponent.uploadFile(paras, localFileName, content, url, type);
		//System.out.println(s);
	}
	
}
