package uk.co.kyleharrison.jobseeker.connectors;

import java.io.IOException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.kyleharrison.jobseeker.model.TwitterPojo;

public class TwitterConnector {

	TwitterPojo TWP;
    
	public TwitterConnector(){
		TWP = new TwitterPojo();
	}

	public void search(){
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(TWP.getConsumerKey(),TWP.getConsumerSecret());
        consumer.setTokenWithSecret(TWP.getAccessToken(), TWP.getAccessSecret());
        HttpGet request = new HttpGet(TWP.buildTwitterURI());
        
        try {
			consumer.sign(request);
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
 
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
		try {
			response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
	        System.out.println(IOUtils.toString(response.getEntity().getContent()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
