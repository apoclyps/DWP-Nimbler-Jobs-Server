package uk.co.kyleharrison.jobseeker.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.jobseeker.interfaces.ConnectorInterface;
import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;
import uk.co.kyleharrison.jobseeker.utils.JsonReader;

public class RequestJSON {

	private JsonReader JSR;
	private JSONObject json = null;
	private JSONArray JSArray = null;
	private ConnectorInterface ConInf;
	private JobInterface JobInf;

	public RequestJSON() {
		this.JSR = new JsonReader();
		this.JSArray = new JSONArray();
	}
	
	public RequestJSON(ConnectorInterface _ConInf, JobInterface _JobInf) {
		this.JSR = new JsonReader();
		this.JSArray = new JSONArray();
		this.ConInf = _ConInf;
		this.JobInf = _JobInf;
	}

	public JSONObject Search() {
		try {
			try{
				System.out.println("ConInf.getTargetURL()"+ConInf.getTargetURL());
			}catch(NullPointerException e){
				e.getStackTrace();
			}
			this.json = JSR.readJsonFromUrl("http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=dundee&sort=" +
					"&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=uk&chnl=" +
					"&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2");
			this.JSArray = this.json.getJSONArray("results");
			System.out.println(this.JSArray.get(0).toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.json;
	}
	
	public void mapToPojo() {
		try {
			JobInf = new ObjectMapper().readValue(JSArray.get(1).toString(),
					JobInf.getClass());
			System.out.println(JobInf.getJobTitle());
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

	public JSONArray getJSArray() {
		return JSArray;
	}

}
