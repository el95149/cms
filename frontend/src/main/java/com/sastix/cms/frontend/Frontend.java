/**
 * 
 */
package com.sastix.cms.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot sample CMS front-end app
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */

@SpringBootApplication(scanBasePackages="com.sastix.cms")
public class Frontend {
	
	static final Logger LOG = (Logger) LoggerFactory.getLogger(Frontend.class);
	
	public static void main(String[] args) {
		LOG.info("**** Staring Sastix CMS Front-end****");
		SpringApplication.run(Frontend.class, args);
	}
	
}
