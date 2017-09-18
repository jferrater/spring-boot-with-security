package com.axiomatics.spring.boot;

import java.io.IOException;

import com.axiomatics.spring.boot.pdp.response.PDPResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    private static ObjectMapper responseReader = new ObjectMapper();

    private JsonUtility(){}
    
    static {
        responseReader.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        responseReader.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static PDPResponse getPDPResponse(String responseBody) throws JsonParseException, JsonMappingException, IOException {
    	System.out.println("Response ----------------------------------------------\n");
    	System.out.println(responseBody);
    	return responseReader.readValue(responseBody, PDPResponse.class);
    }
}
