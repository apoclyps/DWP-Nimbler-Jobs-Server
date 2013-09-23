package uk.co.kyleharrison.jobseeker.connectors;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.jobseeker.interfaces.ConnectorInterface;
import uk.co.kyleharrison.jobseeker.model.IndeedPojo;
import uk.co.kyleharrison.jobseeker.utils.JsonReader;

public class IndeedConnector implements ConnectorInterface {

	private JsonReader JSR;
	private JSONObject json = null;
	private IndeedPojo IDP = null;
	private JSONArray JSArray = null;

	private String targetURL ="http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=dundee&sort=&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=uk&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";

	public IndeedConnector(){
		JSR = new JsonReader();
	}

	public JSONObject Search(){
		try {
			json = JSR.readJsonFromUrl(buildURI());
			JSArray = json.getJSONArray("results");
			System.out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/*public JSONObject Search(){
		try {
			json = JSR.readJsonFromUrl("http://maps.googleapis.com/maps/api/geocode/json?address=dd11jr&sensor=false");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}*/

	public String buildURI(){
		return "http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=dundee&sort=" +
				"&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=uk&chnl=" +
				"&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	}
	
	public void mapToPojo(){
		try {
			IDP = new ObjectMapper().readValue(JSArray.get(1).toString(), IndeedPojo.class);
			System.out.println(IDP.getJobTitle());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getTargetURL() {
		return this.targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

}
