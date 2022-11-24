package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Registeredusers {

	@Id
	private String	username; 
	private String	name;
	private String emailid;
	private String	mobile;
	private String password;
	
	
	public Registeredusers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Registeredusers(String username, String name, String emailid, String mobile, String password
			) {
		super();
		this.username = username;
		this.name = name;
		this.emailid = emailid;
		this.mobile = mobile;
		this.password = password;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		String encryptedPwd = pwdEncoder.encode(password);
		this.password = encryptedPwd;
	}



	@Override
	public String toString() {
		return "Registeredusers [username=" + username + ", name=" + name + ", emailid=" + emailid + ", mobile="
				+ mobile + ", password=" + password + "]";
	}



	
}
