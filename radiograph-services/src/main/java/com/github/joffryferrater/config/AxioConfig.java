package com.github.joffryferrater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axiomatics.spring.boot.PdpService;

@Configuration
public class AxioConfig {

	@Bean
	public PdpService getPdpService() throws Exception {
		return new PdpService();
	}
	
}
