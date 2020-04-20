package com.stacksimplify.restservices;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
//First Spring Boot Application and push the code in GIT Repository through STS tools

@SpringBootApplication
public class SpringbootBuildingblocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBuildingblocksApplication.class, args);
	}
	
	@Bean
	public AcceptHeaderLocaleResolver localResolver()
	{
		AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
		
		acceptHeaderLocaleResolver.setDefaultLocale(Locale.US);
		
		return acceptHeaderLocaleResolver;
	}

	@Bean
	public ResourceBundleMessageSource bundleMessageSource()
	{
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		
		bundleMessageSource.setBasename("messages");
		
		return bundleMessageSource;
	}
	
}
