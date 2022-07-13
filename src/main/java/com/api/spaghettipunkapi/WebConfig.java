package com.api.spaghettipunkapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
	
	/*
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("http://domain2.com")
			.allowedMethods("PUT", "DELETE")
			.allowedHeaders("header1", "header2", "header3")
			.exposedHeaders("header1", "header2")
			.allowCredentials(false).maxAge(3600);
	}*/
}
