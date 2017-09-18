package com.github.joffryferrater.security;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axiomatics.spring.boot.PdpConfiguration;
import com.axiomatics.spring.boot.RequestUtility;

@Configuration
public class Configurations {

	@Bean
	public RequestUtility getRequestUtility() throws Exception {
		return new RequestUtility(getPdpConfig());
	}
	
	@Bean
	public PdpConfiguration getPdpConfig() {
		return new PdpConfiguration();
	}
}
