package cn.orgid.common.sms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import cn.orgid.common.exception.ApplicationException;

public class TencentSmsAPI {

	private static final String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";

	public static boolean sendSms(String appId, String appKey, Message message) {

		Random r = new Random();
		int r1 = r.nextInt();
		long t1 = new Date().getTime()/1000;
		message.setTime(t1);
		String sign = buildSign(appKey, message, r1, t1);
		message.setSig(sign);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url + "?sdkappid=" + appId + "&random=" + r1).post(RequestBody
						.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(message)))
				.build();
		try {
			Response response = client.newCall(request).execute();
			String ret = response.body().string();
			JSONObject jsonObject = JSON.parseObject(ret);
			int  code = jsonObject.getIntValue("result");
			if(code==0){
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	private static String buildSign(String appKey, Message message, int r1, long t1) {

		try {
			return strToHash(String.format(
	        		"appkey=%s&random=%d&time=%d&mobile=%s",
	        		appKey, r1, t1, message.getTel().getMobile()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		throw new ApplicationException("buildSign error");
	}

	
	
	public static class Message {
		
		public static final int TypeNotify=0;
		
		public static final int TypeMarketing=1;

		private Tel tel;

		private int type;

		private String msg;

		private String sig;

		private long time;

		public Tel getTel() {
			return tel;
		}

		public void setTel(Tel tel) {
			this.tel = tel;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getSig() {
			return sig;
		}

		public void setSig(String sig) {
			this.sig = sig;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

	}

	public static class Tel {

		private String nationcode;

		private String mobile;
		
		public static Tel cnTel(String mobile){
			
			
			Tel t = new Tel();
			t.setMobile(mobile);
			t.setNationcode("86");
			return t;
					
		}

		public String getNationcode() {
			return nationcode;
		}

		public void setNationcode(String nationcode) {
			this.nationcode = nationcode;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

	}

	protected static String strToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] inputByteArray = str.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }

    public  static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
}

	public static void main(String args[]) {

		Message message = new Message();
		Tel tel = new Tel();
		tel.setMobile("18658150769");
		tel.setNationcode("86");
		message.setTel(tel);
		message.setMsg("你的验证码是123123，请于1分钟内填写。");
		message.setType(0);
		TencentSmsAPI.sendSms("1400034182", "7907157f4db22b17a8e331c5d13a0f84", message);

	}

}
