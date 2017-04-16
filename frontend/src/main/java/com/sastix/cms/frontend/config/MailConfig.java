package com.sastix.cms.frontend.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *  Mail configuration.
 */
@Configuration
public class MailConfig {
    
    private static final String PROPERTIES_FILE = "configuration.properties";
    private static final String JAVA_MAIL_FILE = "javamail.properties";
    
    private static final String HOST = "mail.server.host";
    private static final String PORT = "mail.server.port";
    private static final String PROTOCOL = "mail.server.protocol";
    private static final String USERNAME = "mail.server.username";
    private static final String PASSWORD = "mail.server.password";

	private static final Logger LOG = (Logger) LoggerFactory.getLogger(MailConfig.class);

    public static final String ENCODING = "UTF-8";

    @Autowired
    private SpringTemplateEngine templateEngine;
    
    private Properties configProperties() throws IOException {
    	Properties properties = new Properties();
    	properties.load(new ClassPathResource(PROPERTIES_FILE).getInputStream());
    	return properties;
    }
    
    private Properties javaMailProperties() throws IOException {
    	Properties properties = new Properties();
    	properties.load(new ClassPathResource(JAVA_MAIL_FILE).getInputStream());
    	return properties;
    }

    /**
     * THYMELEAF: Template Resolver for email templates.
     */
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding(ENCODING);
        templateResolver.setOrder(1);
        return templateResolver;
    }

    @Bean
	public JavaMailSender mailSender() throws IOException {
        Properties properties = configProperties();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(properties.getProperty(HOST));
		mailSender.setPort(Integer.parseInt(properties.getProperty(PORT)));
		mailSender.setProtocol(properties.getProperty(PROTOCOL));
		mailSender.setUsername(properties.getProperty(USERNAME));
		mailSender.setPassword(properties.getProperty(PASSWORD));
        mailSender.setJavaMailProperties(javaMailProperties());
		return mailSender;
    }

}
