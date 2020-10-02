package com.group7.banking.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class EmailConfig {
    @Value("${email.host}")
    private String host;
	
	@Value("${email.port}")
	private int port;
	
	@Value("${email.username}")
	private String username;
	
	@Value("${email.password}")
	private String password;
	
	@Value("${email.smtp.auth}")
	private Boolean auth;
	
	@Value("${email.smtp.starttls.enable}")
	private Boolean starttlsEnabled;
	
	@Value("${email.smtp.connectiontimeout}")
	private float connectionTimeout;
	
	@Value("${email.smtp.timeout}")
	private float timeout;
	
	@Value("${email.smtp.writetimeout}")
	private float writeTimeout;
	
	@Value("${email.smtp.debug}")
	private Boolean debugEnabled;
	
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnabled);
        props.put("mail.smtp.timeout", timeout);
        props.put("mail.smtp.connectiontimeout", connectionTimeout);
        props.put("mail.smtp.writetimeout", writeTimeout);
        props.put("mail.debug", debugEnabled);
        
        return sender;
    }
}