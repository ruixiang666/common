package cn.orgid.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncrypAES {
	
	
	
	private String keyStoreFile;
	
	private Cipher c;
	
	private byte[] key;
	
	
	public String getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}
	
	

	public void init() {
		
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			c = Cipher.getInstance("AES");
			File f = new File(keyStoreFile);
			if(!f.exists()||f.length()==0){
				KeyGenerator keygen= KeyGenerator.getInstance("AES");
				SecureRandom random = new SecureRandom();
				keygen.init(random);
				SecretKey key = keygen.generateKey();
				byte[] bs= key.getEncoded();
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(bs);
				fos.close();
			}
			FileInputStream fis = new FileInputStream(f);
			key=new byte[fis.available()];
			fis.read(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public  byte[] Encrytor(String str)
			 {
		
		try{
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			c.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] src = str.getBytes();
			byte[] cipherByte = c.doFinal(src);
			return cipherByte;
		}catch(Throwable e){
			e.printStackTrace();
		}
		return new byte[0];
		
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff)
			 {
		try{
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			c.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] cipherByte = c.doFinal(buff);
			return cipherByte;
		}catch(Throwable e){
			e.printStackTrace();
			return new byte[0];
		}
		
	}
	
	public static void main(String[] args) {
		
		EncrypAES e = new EncrypAES();
		e.setKeyStoreFile("./key");
		e.init();
		
			//System.out.println(new String(e.Encrytor("abc")));
			
			//System.out.println(new String(e.Decryptor(e.Encrytor("abc"))));
		
		
	}
	

}
