package com.axiomatics.spring.boot;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public abstract class AbstractXACMLSecurityExpressionRoot extends SecurityExpressionRoot
		implements MethodSecurityExpressionOperations {

	private Object filterObject;
	private Object returnObject;
	
	public AbstractXACMLSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}
	
	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}
	
	@Override
	public Object getThis() {
		return this;
	}
	
	@Override
	public void setFilterObject(Object obj) {
		this.filterObject = obj;
	}
	
	@Override
	public void setReturnObject(Object obj) {
		this.returnObject = obj;
	}
}
