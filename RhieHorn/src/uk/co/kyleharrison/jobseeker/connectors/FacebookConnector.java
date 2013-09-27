package uk.co.kyleharrison.jobseeker.connectors;

import java.util.Arrays;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FacebookConnector {

	private String appid = "206816809492506";
	private String appsecret ="ac22aed2ea82b34541f9e0b1d87d8940";
	private String accessToken = "CAACEdEose0cBAN6XNYGGCMuc2zCOrb9u6ZCZBtNArJZAIvhjGG8EPYGiQYyc4J3ndFf1KXJaJDOr6fGImyyuQKKOnAV0Msh1YXfoXMZBMuL8VGy4UBL0fDySXd20iiUZAz3MAx1NGscUdaNihWoC9ZCRxonvsFZBTFtHjjucEXZCXm0OME0PEnmitsPeczgU6c4ZD";
	private FacebookClient facebookClient;
	FacebookClient publicOnlyFacebookClient;
	
	public FacebookConnector(){
		
		AccessToken _accessToken = new DefaultFacebookClient().obtainAppAccessToken(appid,appsecret);
		String token=_accessToken.getAccessToken();
		System.out.println("My application access token: " + token);

		facebookClient = new DefaultFacebookClient("CAACEdEose0cBAItIRQKzVYz54gKk47T89H8k4rOenhhrV0pfZAmt90v65gAlU7my5oJn1Ru7qmmwKPHIKLI3mMJObzmZB05qSUIqIR78Uyg6QPhJ1NExhEfmEDmVsqesPcmwEOiZCOayZBngEltmPfkM4BmmYXF297W0f6lr6jDW3IvRFXQFa8vGlPGIPqQZD");
		publicOnlyFacebookClient = new DefaultFacebookClient();
	}
	
	public void search(){

		/*Connection<Post> publicSearch =
		  facebookClient.fetchConnection("search", Post.class,
		    Parameter.with("q", "Dundee Jobs"), Parameter.with("type", "post"));

		Connection<Page> publicSearch2 =
				  facebookClient.fetchConnection("search", Page.class,
				    Parameter.with("q", "Dundee Jobs"), Parameter.with("type", "page"));
		
		Connection<User> targetedSearch =
		  facebookClient.fetchConnection("me/home", User.class,
		    Parameter.with("q", "Mark"), Parameter.with("type", "user"));
		*/
		
		Connection<Post> publicSearch = publicOnlyFacebookClient
	            .fetchConnection(
	                    "search",
	                    Post.class,
	                    Parameter.with("q", "apple | oranges"),
	                    Parameter.with("type", "post"));
		
		System.out.println("Public search: " + publicSearch.getData().get(0).getMessage());
		
		/*
		System.out.println("Public Search:"+publicSearch.getData().size());
		for(int i=0;i<publicSearch.getData().size();i++){
			System.out.println("Public search: " + publicSearch.getData().get(i).getMessage());
		}
		*/
		 //System.out.println("Public search: " + publicSearch.getData().get(0).getMessage());
		//System.out.println("Posts on my wall by friends named Mark: " + targetedSearch.getData().size());
		//fetchObjects();
	}
	
	public void fetchObjects(){
		FetchObjectsResults fetchObjectsResults =
				  facebookClient.fetchObjects(Arrays.asList("me", "Search4Dundee.Jobs"), FetchObjectsResults.class);
		System.out.println("Page likes: " + fetchObjectsResults.page.getLikes());

	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static class FetchObjectsResults {
		  @Facebook
		  User me;

		  // If the Facebook property name doesn't match
		  // the Java field name, specify the Facebook field name in the annotation.

		  @Facebook("Search4Dundee.Jobs")
		  Page page;
		}
	
}

