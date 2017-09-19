package com.github.joffryferrater.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.stereotype.Component;

import com.github.joffryferrater.config.AxioConfig;

@Component
public class XACMLWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

	@Autowired
	AxioConfig configurations;
	
	private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	/**
	 * The use of 'new' to create the XACMLSecurityExpressionRoot instance will
	 * not work because it will not be visible to the Spring Security framework
	 * and hence all autowiring etc. will not be available to the instance.
	 * <p>
	 * Use the method below instead.
	 */
	protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
			FilterInvocation fi) {
		WebSecurityExpressionRoot root = null;
		try {
			root = new XACMLWebSecurityExpressionRoot(authentication, fi, configurations.getPdpService());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.setPermissionEvaluator(getPermissionEvaluator()); // Expected by
																// WebSecurityExpressionRoot.
																// Don't change
		root.setTrustResolver(trustResolver); // Expected by
												// WebSecurityExpressionRoot.
												// Don't change
		root.setRoleHierarchy(getRoleHierarchy()); // Expected by
													// WebSecurityExpressionRoot.
													// Don't change
		return root;
	}

}
