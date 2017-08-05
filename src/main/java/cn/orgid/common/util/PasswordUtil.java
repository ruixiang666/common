package cn.orgid.common.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.orgid.common.exception.ApplicationException;
import cn.orgid.common.util.MD5;

public class PasswordUtil {
	
	
	
	public static String securePassword(String passwd){
		
		if(StringUtils.isBlank(passwd)||passwd.trim().length()<6){
			throw new ApplicationException("密码长度不足6位");
		}
		String _md=MD5.md5(passwd);
		char[] cs =_md.toCharArray();
		int val=0;
		for(int i=0;i<cs.length;i++){
			val+=Math.abs(((int)cs[i]))%passwd.length();
		}
		String md =passwd;
		for(int i=0;i<=val;i++){
			md=MD5.md5(md);
		}
		return md;
				
	}
	
	public static boolean match(String initPasswd,String securePassword){
		
		if(initPasswd==null||securePassword==null){
			throw new ApplicationException("参数不能为空");
		}
		return securePassword(initPasswd).equals(securePassword);
		
	}
	
	public static void ensureStrength(String passwd){
		
		String error=null;
		if(passwd==null||passwd.trim().length()<8){
			error="口令不能少于8个字符";
		}
		char[] cs= passwd.toCharArray();
		int lowerCount=0;
		int upperCount=0;
		int digitCount=0;
		Set<Character> set = new HashSet<Character>();
		for (char c : cs) {
			if(Character.isDigit(c)){
				digitCount++;
			}
			if(Character.isLowerCase(c)){
				lowerCount++;
			}
			if(Character.isUpperCase(c)){
				upperCount++;
			}
			set.add(Character.valueOf(Character.toLowerCase(c)));
		}
		if(set.size()<cs.length){
			error="口令不能有重复的字符";
		}
		if(lowerCount==0||upperCount==0||digitCount==0){
			error="口令必须包含大小写字符和数字";
		}
		if(error!=null){
			throw new ApplicationException(error);
		}
		
	}
	
	
	public static void main(String[] args) {
		
		
		
	}
	

}
