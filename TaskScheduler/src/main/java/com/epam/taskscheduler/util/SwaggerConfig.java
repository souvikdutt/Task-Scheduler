package com.epam.taskscheduler.util;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	 
	 // Adding Swagger Configuration
	 @Bean
	 public Docket swaggerConfiguration() {
	     return new Docket(DocumentationType.SWAGGER_2)
	                 .select()
	                 .paths(PathSelectors.ant("/**")) // URL where APIs are exposed, so /error is removed
	                 .apis(RequestHandlerSelectors.basePackage("com.epam.taskscheduler.restcontroller")) // Package base configuration, which packages to look at
	                 .build()
	     			.apiInfo(apiDetails());
	                
	 }
	
	 // Adding Application metadata
	 private ApiInfo apiDetails() {
	     return new ApiInfo("Task Management API",
	                         "An Spring Boot RESTful API to manage all tasks and notes",
	                         "1.0",
	                         "Free to use",
	                         new Contact("Souvik Dutta", "https://www.google.com", "Souvik_Dutta@epam.com"),
	                         "API License",
	                         "https://www.google.com",
	                         Collections.emptyList());
	 }
	 
}