//package com.axiomatics.spring.boot;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.axiomatics.spring.boot.pdp.request.AccessSubject;
//import com.axiomatics.spring.boot.pdp.request.Attribute;
//import com.axiomatics.spring.boot.pdp.request.PDPRequest;
//import com.axiomatics.spring.boot.pdp.request.Request;
//import com.axiomatics.spring.boot.pdp.response.PDPResponse;
//
//@RestController
//@RequestMapping("/pdp_test")
//public class PDPController {
//
//	private RequestUtility requestUtility;
//	
//	@Autowired
//	public PDPController(RequestUtility requestUtility) {
//		this.requestUtility = requestUtility;
//	}
//	
//    @RequestMapping(value="/access_subject_with_datatype", method = RequestMethod.GET)
//    public HttpEntity<PDPResponse> accessSubjectRequest(
//    		@RequestParam("attributeId") String attributeId,
//    		@RequestParam("dataType") String dataType,
//    		@RequestParam("values") List<Object> values) {
//    	PDPRequest pdpRequest = new PDPRequest();
//        try {
//        	Request request = new Request();
//        	AccessSubject accessSubject = new AccessSubject();
//        	Attribute attribute = new Attribute();
//        	attribute.withAttributeId(attributeId).withDataType(dataType).withValues(values);
//        	accessSubject.withAttributes(attribute);
//        	request.withAccessSubject(accessSubject);
//        	pdpRequest.withRequest(request);
//        	PDPResponse pdpResponse = requestUtility.pdpResponseEntity(pdpRequest);
//        	return new ResponseEntity<>(pdpResponse,
//                    HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public HttpEntity<String> deleteTest() {
//    	return new ResponseEntity<>("deleted", HttpStatus.OK);
//    }
//    
//    @RequestMapping(value="/access_subject", method = RequestMethod.GET)
//    public HttpEntity<PDPResponse> accessSubjectRequest(
//    		@RequestParam("attributeId") String attributeId,
//    		@RequestParam("values") List<Object> values) {
//        try {
//        	PDPRequest pdpRequest = new PDPRequest();
//        	Request request = new Request();
//        	AccessSubject accessSubject = new AccessSubject();
//        	Attribute attribute = new Attribute();
//        	attribute.withAttributeId(attributeId).withValues(values);
//        	accessSubject.withAttributes(attribute);
//        	request.withAccessSubject(accessSubject);
//        	pdpRequest.withRequest(request);
//        	PDPResponse pdpResponse = requestUtility.pdpResponseEntity(pdpRequest);
//        	return new ResponseEntity<>(pdpResponse,
//                    HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
