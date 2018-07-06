package com.pactera.utlis;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pactera.config.spring.SpringUtil;

public class PasswordUtil {
	
	public static String encode(String password){
		PasswordEncoder bean = SpringUtil.getBean(BCryptPasswordEncoder.class);
		if(bean==null) bean=new BCryptPasswordEncoder();
		return bean.encode(password);
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		System.out.println(encode);
	}
}
