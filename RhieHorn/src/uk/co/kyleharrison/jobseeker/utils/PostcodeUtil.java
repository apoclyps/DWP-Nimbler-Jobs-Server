package uk.co.kyleharrison.jobseeker.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.jobseeker.utils.JsonReader;

public class PostcodeUtil {

	private JsonReader JSR;
	private JSONObject json = null;
	private JSONArray JSArray = null;
	private String targetURL = "http://maps.googleapis.com/maps/api/geocode/json?address=dd11jr&sensor=false";

	public PostcodeUtil(){
		
	}
	
	public void Search(){
		try {
			json = JSR.readJsonFromUrl(targetURL);
			JSArray = json.getJSONArray("results");
			System.out.println(JSArray.get(0).toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String buildIndeedURI(){
		this.targetURL = "http://maps.googleapis.com/maps/api/geocode/json?address=dd11jr&sensor=false";
		return this.targetURL;
	}
	
	  public static String excutePost(String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
	  
	  public void search(){
			try {
			    URL url = new URL(targetURL);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			    String line;
			    
			    while ((line = reader.readLine()) != null) {
			        System.out.println(line.toString());
			        //jsonString += line;
			    }
			    reader.close();

			} catch (MalformedURLException e) {
			    e.getStackTrace();
			} catch (IOException e) {
			    e.getStackTrace();
			}

			//System.out.println(jsonString);
			JsonParser jsp = JsonParser.getInstance();
			//jsp.parseResults(jsonString);
			
		
			// JSONObject json = (JSONObject) JsonSerializer.toJSON( jsonString );        
		   //     JSONArray pilot = jsonString.getJSONArray("results");

		        
		       // JSONObject obj = (JSONObject) pilot.getJSONObject(0);
		       // System.out.println("Results "+ obj.get("jobtitle"));

		 /*
		 JSONObject res = new JSONObject(city);
							JSONArray mapsData = res.getJSONArray("results");
							
						
							JSONObject test1 = mapsData.getJSONObject(0);
							JSONArray jArr = test1.getJSONArray("address_components");
							for(int i=0;i<jArr.length();i++){
								if (jArr.getJSONObject(i).getJSONArray("types").getString(0).equals("locality")){
									city = jArr.getJSONObject(i).getString("long_name");
								}
							}
							*/
	  }

	public JSONArray getJSArray() {
		return JSArray;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setJSArray(JSONArray jSArray) {
		JSArray = jSArray;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}
	
}
