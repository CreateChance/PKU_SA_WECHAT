package cn.edu.pku.wechat.model;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3559675973332885641L;
	private String user_id;
	private String mobile;
	private String email;
	private String pwd;
	private Date register_time;
	private Date last_login_time;
	private String nickname;
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the register_time
	 */
	public Date getRegister_time() {
		return register_time;
	}
	/**
	 * @param register_time the register_time to set
	 */
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	/**
	 * @return the last_login_time
	 */
	public Date getLast_login_time() {
		return last_login_time;
	}
	/**
	 * @param last_login_time the last_login_time to set
	 */
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
