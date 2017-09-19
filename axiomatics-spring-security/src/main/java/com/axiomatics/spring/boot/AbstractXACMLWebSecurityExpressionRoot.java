package com.axiomatics.spring.boot;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.axiomatics.spring.boot.pdp.request.AccessSubject;
import com.axiomatics.spring.boot.pdp.request.Attribute;
import com.axiomatics.spring.boot.pdp.request.PDPRequest;
import com.axiomatics.spring.boot.pdp.request.Request;
import com.axiomatics.spring.boot.pdp.response.PDPResponse;

public abstract class AbstractXACMLWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

	private PdpService pdpService;

	public AbstractXACMLWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpService pdpService) {
		super(a, fi);
		this.pdpService = pdpService;
	}

	public boolean XACMLDecisionURL(String attributeId, String dataType, Collection<Object> values) {
		PDPRequest pdpRequest = new PDPRequest();
		Request req = new Request();
		AccessSubject accessSubject = new AccessSubject();
		Attribute attribute = new Attribute();
		attribute.withAttributeId(attributeId).withDataType(dataType).withValues(values);
		accessSubject.withAttributes(attribute);
		req.withAccessSubject(accessSubject);
		pdpRequest.withRequest(req);
		String responseBody = null;
		try {
			responseBody = pdpService.getPdpResponse(pdpRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isPermitted(responseBody);
	}
	
	private boolean isPermitted(String responseBody) {
		boolean isPermit = false;
		try {
			PDPResponse pdpResponse = JsonUtility.getPDPResponse(responseBody);
			if("Permit".equals(pdpResponse.getResponse().getDecision())) {
				isPermit =  true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isPermit;
	}
}