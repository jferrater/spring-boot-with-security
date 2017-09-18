package com.axiomatics.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class AxiomaticsSpringBootApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AxiomaticsSpringBootApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(AxiomaticsSpringBootApplication.class, args);
	}
}
