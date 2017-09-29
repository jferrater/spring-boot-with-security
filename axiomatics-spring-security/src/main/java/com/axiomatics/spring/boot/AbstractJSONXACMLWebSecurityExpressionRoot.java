package com.axiomatics.spring.boot;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import com.axiomatics.spring.boot.pdp.request.AccessSubject;
import com.axiomatics.spring.boot.pdp.request.Action;
import com.axiomatics.spring.boot.pdp.request.Attribute;
import com.axiomatics.spring.boot.pdp.request.AttributeDefinition;
import com.axiomatics.spring.boot.pdp.request.Environment;
import com.axiomatics.spring.boot.pdp.request.PDPRequest;
import com.axiomatics.spring.boot.pdp.request.Request;
import com.axiomatics.spring.boot.pdp.request.Resource;
import com.axiomatics.spring.boot.pdp.response.PDPResponse;

public abstract class AbstractJSONXACMLWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

	private PdpService pdpService;

	private AccessSubject accessSubject;
	private Action action;
	private Environment environment;
	private Resource resource;
	
	public AbstractJSONXACMLWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpService pdpService) {
		super(a, fi);
		this.pdpService = pdpService;
		initializeRequestAttributes();
	}

	private void initializeRequestAttributes() {
		accessSubject = new AccessSubject();
		action = new Action();
		environment = new Environment();
		resource = new Resource();
	}

	public abstract List<AccessSubject> createAccessSubjectAttributes();
	
	public abstract List<Action> createActionAttributes();

	public abstract List<Environment> createEnvironmentAttributes();

	public abstract List<Resource> createResourceAttributes();
	
	public abstract List<AttributeDefinition> createAttributeDefinition();
	
	public boolean XACMLDecisionURL(String category, String attributeId, String dataType,
			List<Object> values) {
		Request request = new Request();
		Resource resource = new Resource();
		Environment environment = new Environment();
		switch (category) {
			case "ACCESS-SUBJECT":
				accessSubject.addAttribute(new Attribute().withAttributeId(attributeId).withDataType(dataType).withValues(values));
				break;
			case "ACTION":
				action.addAttribute(new Attribute().withAttributeId(attributeId).withDataType(dataType).withValues(values));
				break;
			case "RESOURCE":
				resource.addAttribute(new Attribute().withAttributeId(attributeId).withDataType(dataType).withValues(values));
				break;
			case "ENVIRONMENT":
				environment.addAttribute(new Attribute().withAttributeId(attributeId).withDataType(dataType).withValues(values));
				break;
			default:
		}
		setAccessSubjectAttribute(request);
		setActionAttribute(request);
		setResourceAttribute(request);
		setEnvironmentAttribute(request);
		addCustomAccessSubjectAttributes(request);
		addCustomActionAttributes(request);
		addCustomResourceAttributes(request);
		addEnvironmentAttributes(request);
		String response;
		try {
			PDPRequest pdpRequest = createPdpRequest(request);
			response = pdpService.getPdpResponse(pdpRequest);
		} catch (IOException e) {
			return false;
		}
		return isPermitted(response);
	}

	private void setAccessSubjectAttribute(Request request) {
		if(!accessSubject.getAttributes().isEmpty()) {
			request.withAccessSubject(accessSubject);
		}
	}
	
	private void setActionAttribute(Request request) {
		if(!action.getAttributes().isEmpty()) {
			request.withAction(action);
		}
	}
	
	private void setResourceAttribute(Request request) {
		if(!resource.getAttributes().isEmpty()) {
			request.withResource(resource);
		}
	}
	
	private void setEnvironmentAttribute(Request request) {
		if(!environment.getAttributes().isEmpty()) {
			request.withEnvironment(environment);
		}
	}
	
	
	private void addCustomAccessSubjectAttributes(Request request) {
		List<AccessSubject> accessSubjects = createAccessSubjectAttributes();
		if (accessSubjects == null) {
			return;
		}
		accessSubjects.forEach(as -> request.addAccessSubject(as));
	}

	private void addEnvironmentAttributes(Request request) {
		List<Environment> environments = createEnvironmentAttributes();
		if(environments == null) {
			return;
		}
		environments.forEach(env -> request.addEnvironment(env));
	}
	
	private void addCustomResourceAttributes(Request request) {
		List<Resource> resources = createResourceAttributes();
		if (resources == null) {
			return;
		}
		resources.forEach(res -> request.addResource(res));
	}
	
	private void addCustomActionAttributes(Request request) {
		List<Action> actions = createActionAttributes();
		if(actions == null) {
			return;
		}
		actions.forEach(a -> request.addActionAttribute(a));
	}

	private PDPRequest createPdpRequest(Request request) {
		return new PDPRequest().withRequest(request);
	}
	
	/**
	 * A Permit Bias evaluation.
	 * 
	 * @param responseBody
	 * @return
	 */
	public boolean isPermitted(String responseBody) {
		System.out.println("PDP response ----------------------------------------------\n");
		System.out.println(responseBody);
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
