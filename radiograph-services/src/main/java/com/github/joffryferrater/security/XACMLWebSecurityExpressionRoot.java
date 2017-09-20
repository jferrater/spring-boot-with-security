package com.github.joffryferrater.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import com.axiomatics.spring.boot.AbstractJSONXACMLWebSecurityExpressionRoot;
import com.axiomatics.spring.boot.PdpService;
import com.axiomatics.spring.boot.pdp.request.AccessSubject;
import com.axiomatics.spring.boot.pdp.request.Action;
import com.axiomatics.spring.boot.pdp.request.Attribute;
import com.axiomatics.spring.boot.pdp.request.AttributeDefinition;
import com.axiomatics.spring.boot.pdp.request.Environment;
import com.axiomatics.spring.boot.pdp.request.Resource;

public class XACMLWebSecurityExpressionRoot extends AbstractJSONXACMLWebSecurityExpressionRoot {

	
	public XACMLWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpService pdpService) {
		super(a, fi, pdpService);
 	}

	@Override
	public List<Environment> createEnvironmentAttributes() {
		return null;
	}

	@Override
	public List<Resource> createResourceAttributes() {
		return null;
	}

	@Override
	public List<AccessSubject> createAccessSubjectAttributes() {
		return null;
	}

	@Override
	public List<Action> createActionAttributes() {
		Action action = new Action();
		Attribute attribute = new Attribute();
		attribute.withAttributeId("role").withDataType("string").withValues("ADMIN");
		action.withAttributes(attribute);
		return Arrays.asList(action);
	}

	@Override
	public List<AttributeDefinition> createAttributeDefinition() {
		return null;
	}

}
