package cn.orgid.common.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import cn.orgid.common.exception.ApplicationException;
import cn.orgid.common.util.MD5;

public class EncrypUtil {

	private static final String AES = "AES";

	public static String encryt(String str, String key) {

		try {
			Cipher c = Cipher.getInstance(AES);
			SecretKeySpec keySpec = new SecretKeySpec(
					ByteArrayUtil.hexStringToByteArray(key), AES);
			c.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] src = str.getBytes();
			byte[] cipherByte = c.doFinal(src);
			return MD5.byte2hex(cipherByte, cipherByte.length);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";

	}

	public static String decrypt(String hex, String key) {

		try {
			Cipher c = Cipher.getInstance(AES);
			SecretKeySpec keySpec = new SecretKeySpec(
					ByteArrayUtil.hexStringToByteArray(key), AES);
			c.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] cipherByte = c.doFinal(MD5.hex2byte(hex));
			return new String(cipherByte);
		} catch (Throwable e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String getEncryptionKey() {

		String encryptionKey = null;
		try {

			KeyGenerator keygen = KeyGenerator.getInstance(AES);
			SecureRandom random = new SecureRandom();
			keygen.init(random);
			SecretKey key = keygen.generateKey();
			encryptionKey = ByteArrayUtil.toHexString(key.getEncoded());

		} catch (Throwable e) {
			throw new ApplicationException(e.getMessage());
		}
		return encryptionKey;

	}

}
