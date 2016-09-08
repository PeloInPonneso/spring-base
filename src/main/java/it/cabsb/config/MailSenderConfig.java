package it.cabsb.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Preconditions;

@Order(6)
@Configuration
@ComponentScan(basePackages = { "it.cabsb.mail" })
public class MailSenderConfig {

	@Autowired
    private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(Preconditions.checkNotNull(env.getProperty("mail.sender.host")));
		mailSender.setPort(Integer.parseInt(Preconditions.checkNotNull(env.getProperty("mail.sender.port"))));
		mailSender.setProtocol(Preconditions.checkNotNull(env.getProperty("mail.sender.protocol")));
		mailSender.setUsername(Preconditions.checkNotNull(env.getProperty("mail.sender.username")));
		mailSender.setPassword(Preconditions.checkNotNull(env.getProperty("mail.sender.password")));
		mailSender.setDefaultEncoding("UTF-8");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.auth")));
		javaMailProperties.put("mail.smtp.starttls.enable", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.starttls.enable")));
		javaMailProperties.put("mail.smtp.starttls.required", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.starttls.required")));
		javaMailProperties.put("mail.smtp.ssl.enable", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.ssl.enable")));
		javaMailProperties.put("mail.smtp.debug", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.debug")));
		javaMailProperties.put("mail.smtp.from", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.from")));
		javaMailProperties.put("mail.smtp.quitwait", Preconditions.checkNotNull(env.getProperty("mail.sender.smtp.quitwait")));
		
		mailSender.setJavaMailProperties(javaMailProperties);	
		return mailSender;
	}
}
