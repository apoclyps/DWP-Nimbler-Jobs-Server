package uk.co.kyleharrison.jobseeker.model;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TwitterPojo {

	private String AccessToken = "55812900-ccMhVt0LpWbLRwKWT6FdUDkV3Z84FLLmdf1675kWI";
	private String AccessSecret = "5DQZob6fp10HhmXS41BRnULiNw3LrpuYoaDhXu2eo";
	private String ConsumerKey = "HeCZ0NzOY9bJrw3oVVR7uw";
	private String ConsumerSecret = "Pa4AjcZcU6gxZzwDgNkx96hZXjx84a12OCjmkBZcfcE";
	
	private String twitterURI;
	private int twitterResponses =200;
	private boolean includeRTS = true;
	private String screenName = "DundeeCityJCP"; //s1jobs
	
	public TwitterPojo(){
		try{			
			  this.ConsumerSecret = (String) new InitialContext().lookup("java:/comp/env/Twitter/ConsumerSecret");
			  this.ConsumerKey = (String) new InitialContext().lookup("java:/comp/env/Twitter/ConsumerKey");
			  this.AccessSecret = (String) new InitialContext().lookup("java:/comp/env/Twitter/AccessSecret");
			  this.AccessToken = (String) new InitialContext().lookup("java:/comp/env/Twitter/AccessToken");
			}catch (NamingException ne)	{	
				System.out.println("Naming Exception in TweetModel.java");
				ne.printStackTrace();
			}
	}
	
	public String buildTwitterURI(){
		
		twitterURI = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="
						+screenName+"&count="+this.twitterResponses+"&include_rts="+(includeRTS==true ? 1 : 0);
		return twitterURI;
	}
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public String getTwitterURI() {
		return twitterURI;
	}

	public void setTwitterURI(String twitterURI) {
		this.twitterURI = twitterURI;
	}

	public int getTwitterResponses() {
		return twitterResponses;
	}

	public void setTwitterResponses(int twitterResponses) {
		this.twitterResponses = twitterResponses;
	}

	public boolean isIncludeRTS() {
		return includeRTS;
	}

	public void setIncludeRTS(boolean includeRTS) {
		this.includeRTS = includeRTS;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public void setAccessSecret(String accessSecret) {
		AccessSecret = accessSecret;
	}

	public void setConsumerKey(String consumerKey) {
		ConsumerKey = consumerKey;
		
	}

	public void setConsumerSecret(String consumerSecret) {
		ConsumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return AccessToken;
	}

	public String getAccessSecret() {
		return AccessSecret;
	}

	public String getConsumerKey() {
		return ConsumerKey;
	}

	public String getConsumerSecret() {
		return ConsumerSecret;
	}
	
}
