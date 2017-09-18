package com.axiomatics.spring.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;


/**
 * Created by Joffry Ferrater on 12/9/2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	private static final String REALM_NAME = "MY_REALM";

	@Autowired
	XACMLWebSecurityExpressionHandler handler;
	
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN")
        .and().withUser("user").password("user123").roles("USER");
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
    	http.csrf().disable()
    	  .authorizeRequests().expressionHandler(handler())
    	  //.anyRequest().fullyAuthenticated()
          .antMatchers(HttpMethod.GET, "/**").access("hasRole('ADMIN')")
          .antMatchers(HttpMethod.DELETE, "/**").access("XACMLDecisionURL('Attributes.access_subject.user', 'string', {'Joffry'})")
          .and().httpBasic().realmName(REALM_NAME).authenticationEntryPoint(getAuthBasicEntryPoint())
          .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
 
    }
    
    @Bean
    public XACMLWebSecurityExpressionHandler handler() {
    	return this.handler;
    }
//    @Bean
//    public AccessDecisionManager defaultAccessDecisionManager() {
//        List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<AccessDecisionVoter<? extends Object>>();
//        voters.add(new WebExpressionVoter());
//      //  voters.add(new CustomVoter());
//
//        return new ConsensusBased (voters);
//    }
    
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
