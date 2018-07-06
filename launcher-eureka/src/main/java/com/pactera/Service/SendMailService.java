package com.pactera.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMailService {
	@Autowired
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String Sender;

	@Async
	public void sendMailService() {
		for (int i = 0; i < 20; i++) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(Sender);
			message.setTo("653468456@qq.com");
			message.setSubject("主题：简单邮件");
			message.setText("测试邮件内容");
			mailSender.send(message);
		}
	}
	
	@Async
	public void sendMailError() {
		throw new RuntimeException();
	}

}
