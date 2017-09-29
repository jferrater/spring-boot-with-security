package com.github.joffryferrater.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private String endpoint;
	private String httpMethod;
	public XACMLWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpService pdpService) {
		super(a, fi, pdpService);
		this.endpoint = fi.getRequestUrl();
		this.httpMethod = fi.getHttpRequest().getMethod();
 	}

	@Override
	public List<Environment> createEnvironmentAttributes() {
		return null;
	}

	@Override
	public List<Resource> createResourceAttributes() {
		/*
		 * Add request endpoint as Resource attribute.
		 */
		Resource resource = new Resource();
		Attribute attribute = new Attribute();
		attribute.withAttributeId("urn:oasis:names:tc:xacml:1.0:resource:resource-id").withDataType("string").withValues(endpoint);
		resource.withAttributes(attribute);
		return Arrays.asList(resource);
	}

	@Override
	public List<AccessSubject> createAccessSubjectAttributes() {
		/*
		 * Addin current user and its role as subject attributes. 
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Object> roles = new ArrayList<>();
		auth.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
		final String username = auth.getName();
		AccessSubject accessSubject = new AccessSubject();
		Attribute usernameAttribute = new Attribute();
		usernameAttribute.withAttributeId("Attributes.access_subject.username").withDataType("string").withValues(username);
		accessSubject.withAttributes(usernameAttribute);
		Attribute roleAttribute = new Attribute();
		roleAttribute.withAttributeId("Attributes.access_subject.role").withDataType("string").withValues(roles);
		accessSubject.addAttribute(roleAttribute);
		return Arrays.asList(accessSubject);
	}

	@Override
	public List<Action> createActionAttributes() {
		/*
		 * Add Http Method as Action attribute.
		 */
		Action action = new Action();
		Attribute attribute = new Attribute();
		attribute.withAttributeId("Attributes.action.httpMethod").withDataType("string").withValues(httpMethod);
		action.withAttributes(attribute);
		return Arrays.asList(action);
	}

	@Override
	public List<AttributeDefinition> createAttributeDefinition() {
		return null;
	}

}
