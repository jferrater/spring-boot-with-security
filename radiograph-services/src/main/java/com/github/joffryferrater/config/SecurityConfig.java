package com.github.joffryferrater.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.github.joffryferrater.security.XACMLWebSecurityExpressionHandler;


/**
 * Created by Joffry Ferrater on 12/9/2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	private static final String REALM_NAME = "MY_REALM";
	@Autowired
	HttpServletRequest context;

	@Autowired
	XACMLWebSecurityExpressionHandler handler;
	
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN")
        .and().withUser("user").password("user123").roles("USER")
        .and().withUser("denyUser").password("user123").roles("DENY_ME");
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	  .authorizeRequests()
    	  .expressionHandler(handler())
    	  .antMatchers("/").permitAll()
          .antMatchers(HttpMethod.DELETE, "/**").access("hasRole('ADMIN')")
          .antMatchers(HttpMethod.GET, "/doctors/**").access("XACMLDecisionURL('ACCESS-SUBJECT', 'Attributes.access_subject.group', 'string', {'doctor'})")
          .and().httpBasic().realmName(REALM_NAME).authenticationEntryPoint(getAuthBasicEntryPoint())
          .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
 
    }
    
    @Bean
    public XACMLWebSecurityExpressionHandler handler() {
    	return this.handler;
    }
    
    @Bean
    public DefaultBasicAuthEntryPoint getAuthBasicEntryPoint() {
    	return new DefaultBasicAuthEntryPoint();
    }
    
    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
