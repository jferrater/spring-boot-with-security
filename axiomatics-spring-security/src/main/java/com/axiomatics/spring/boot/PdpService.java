package com.axiomatics.spring.boot;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axiomatics.spring.boot.config.PdpConfiguration;
import com.axiomatics.spring.boot.pdp.request.PDPRequest;
import com.axiomatics.spring.boot.pdp.response.PDPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PdpService {

	private static final String COLON_SEPARATOR = ":";

	private static final String ISO_8859_1 = "ISO-8859-1";

	private static final String BASIC = "Basic ";

	private static final String XACML_JSON = "application/xacml+json";

	@Autowired
	private PdpConfiguration config;

	private static final String HTTPS = "https://";
	private static final String PDP_ENDPOINT = "/asm-pdp/authorize";
	private CloseableHttpClient client;

	public PdpService() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

	private HttpResponse post(PDPRequest request) throws ClientProtocolException, IOException {
		HttpPost postRequest = new HttpPost(pdpEndpoint());
		postRequest.addHeader(HttpHeaders.CONTENT_TYPE, XACML_JSON);
		postRequest.addHeader(HttpHeaders.AUTHORIZATION, BASIC + new String(encodedAuth()));

		final String jsonBody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
		System.out.println("PDP Request Body ---------------------------------------------------- \n");
		System.out.println(jsonBody);
		StringEntity requestEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
		postRequest.setEntity(requestEntity);
		return client.execute(postRequest);
	}

	PDPResponse pdpResponseEntity(PDPRequest request) throws ClientProtocolException, IOException {
		String responseBody = getResponseAsString(request);
		System.out.println("PDP response ------------------------------------------------------------------------ \n");
		System.out.println(responseBody);
		return JsonUtility.getPDPResponse(responseBody);
	}

	private String pdpEndpoint() {
		StringBuilder endpoint = new StringBuilder();
		endpoint.append(HTTPS).append(config.getIp());
		endpoint.append(COLON_SEPARATOR).append(config.getPort());
		endpoint.append(PDP_ENDPOINT);
		return endpoint.toString();
	}

	private String encodedAuth() {
		StringBuilder userPass = new StringBuilder();
		userPass.append(config.getUsername()).append(COLON_SEPARATOR).append(config.getPassword());
		byte[] encodedAuth = Base64.encodeBase64(userPass.toString().getBytes(Charset.forName(ISO_8859_1)));
		return new String(encodedAuth);
	}

	String getPdpResponse(PDPRequest request) throws ClientProtocolException, IOException {
		return getResponseAsString(request);
	}

	private String getResponseAsString(PDPRequest request) throws ParseException, IOException {
		HttpResponse response = post(request);
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}
}
