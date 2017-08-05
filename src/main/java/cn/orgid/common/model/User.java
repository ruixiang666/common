package cn.orgid.common.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.orgid.common.util.MD5;
import cn.orgid.common.util.PasswordUtil;

@Entity
@Table(name="t_user")
public class User   {

	
	@Column(unique=true)
	private String loginName;
	
	private String passwd;
	
	private String loginToken;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	
	
	public boolean validPasswd(String passwd){
		
		
		return PasswordUtil.match(passwd, this.passwd);
		
	}
	
	public void refreshToken(){
		
		loginToken=MD5.md5(UUID.randomUUID().toString());
		
	}
	
	public boolean validLoginToken(String token){
		return token!=null&&token.equals(loginToken);
	}

	public void encryptPasswd() {
		
		this.passwd=PasswordUtil.securePassword(passwd);
		
	}

	public void clearSensitiveData() {
		
		this.setPasswd(null);
		this.setLoginToken(null);
		this.setLoginName(null);
		
	}

}
