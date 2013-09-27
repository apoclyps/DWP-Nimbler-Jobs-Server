package uk.co.kyleharrison.jobseeker.model;

import java.util.Date;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;

public class LinkedInPojo implements JobInterface {

	private String apiKey ="fev530857s9y";
	private String secretKey ="8w0URbyubedakNfy";
	private String accessToken ="fe957d9a-e88f-4921-9667-4b9cfb9f0b4a";
	private String secretToken ="9d422010-2efd-40a2-9c12-7b3fa3a155fd";
	
	@Override
	public String getJobTitle() {
		return null;
	}
	@Override
	public Date getDate() {
		return null;
	}
	@Override
	public String getSnippet() {
		return null;
	}
	@Override
	public String getURL() {
		return null;
	}
	public String getApiKey() {
		return apiKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public String getSecretToken() {
		return secretToken;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setSecretToken(String secretToken) {
		this.secretToken = secretToken;
	}
	
	
	
}
