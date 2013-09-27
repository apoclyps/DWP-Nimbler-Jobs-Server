package uk.co.kyleharrison.jobseeker.model;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.restfb.FacebookClient.AccessToken;

public class FacebookPojo {

	private String appid = "206816809492506";
	private String appsecret ="ac22aed2ea82b34541f9e0b1d87d8940";
	private String accessTokenStr = "CAACEdEose0cBAN6XNYGGCMuc2zCOrb9u6ZCZBtNArJZAIvhjGG8EPYGiQYyc4J3ndFf1KXJaJDOr6fGImyyuQKKOnAV0Msh1YXfoXMZBMuL8VGy4UBL0fDySXd20iiUZAz3MAx1NGscUdaNihWoC9ZCRxonvsFZBTFtHjjucEXZCXm0OME0PEnmitsPeczgU6c4ZD";
	private AccessToken accessToken;
	
	public FacebookPojo(){
		try{			
			  this.appid = (String) new InitialContext().lookup("java:/comp/env/Facebook/AppID");
			//  this.ConsumerKey = (String) new InitialContext().lookup("java:/comp/env/Facebook/ConsumerKey");
			//  this.AccessSecret = (String) new InitialContext().lookup("java:/comp/env/Facebook/AccessSecret");
			 // this.AccessToken = (String) new InitialContext().lookup("java:/comp/env/Facebook/AccessToken");
			}catch (NamingException ne)	{	
				System.out.println("Naming Exception in TweetModel.java");
				ne.printStackTrace();
			}
	}

	public String getAppid() {
		return appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public String getAccessTokenStr() {
		return accessTokenStr;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public void setAccessTokenStr(String accessTokenStr) {
		this.accessTokenStr = accessTokenStr;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
