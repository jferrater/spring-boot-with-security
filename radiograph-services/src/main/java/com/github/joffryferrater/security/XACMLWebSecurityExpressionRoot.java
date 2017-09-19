package com.github.joffryferrater.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import com.axiomatics.spring.boot.AbstractXACMLWebSecurityExpressionRoot;
import com.axiomatics.spring.boot.PdpService;

public class XACMLWebSecurityExpressionRoot extends AbstractXACMLWebSecurityExpressionRoot {

	
	public XACMLWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpService pdpService) {
		super(a, fi, pdpService);
 	}

}
