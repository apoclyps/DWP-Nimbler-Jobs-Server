package uk.co.kyleharrison.jobseeker.connectors;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.jobseeker.interfaces.ConnectorInterface;
import uk.co.kyleharrison.jobseeker.model.YagaPojo;
import uk.co.kyleharrison.jobseeker.utils.JsonReader;

import com.as400samplecode.util.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YagaJobsConnector implements ConnectorInterface {

	private JsonReader JSR;
	private JSONObject json = null;
	private YagaPojo YagaJobsP = null;
	private JSONArray JSArray = null;
	private MySQLConnector MySQLCon;
    ArrayList<Object> jobs = new ArrayList<>();
	
    //Kyle Key
	//private String apiKey="92c07bf9353c26cd36b41d91f3190fde";
    
    //Ross Key
    private String apiKey ="1e53e2c1529e0386f3f2423d4cba4305";
	public String location="dundee";
	public int offset =0;

	public YagaJobsConnector(){
		JSR = new JsonReader();
		MySQLCon = new MySQLConnector();
	}

	public JSONObject Search(){
		try {
			json = JSR.readJsonFromUrl(buildURI());
			JSArray = json.getJSONArray("JSON");
			System.out.println(JSArray.length());
			System.out.println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	

	public String buildURI(){
		return "http://yagajobs.co.uk/api/vacancies.json/search?&api_key="+apiKey+
				"&location="+location+"&offset="+offset;
	}

	public String getTargetURL() {
		System.out.println("URL = "+ buildURI());
		return buildURI();
	}
	
	
	public void mapToPojo(org.json.JSONArray jsonA){
		
		try {
			System.out.println("Mapping Test :"+ jsonA.get(0));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		try {
			for(int i =0; i<jsonA.length();i++)
			{
				YagaJobsP = new ObjectMapper().readValue(jsonA.get(i).toString(), YagaPojo.class);	
				System.out.println("Yaga Test "+YagaJobsP.getJobBoard());
				insertPojoToDB(YagaJobsP);
			}
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
	

	public void insertPojoToDB(YagaPojo YagaJobsP){
		
		String sqlStatement = MySQLCon.GenerateSqlStatement("yagajobs", YagaJobsP);
		MySQLCon.insetProcedure(sqlStatement,YagaJobsP);
	}

	public ArrayList<Object> getJobs(){
		
		//MySQLCon.Select Jobs
		jobs = MySQLCon.queryDatabase("select * from yagajobs LIMIT 5;");
		
		return jobs;
	}
	
	public JSONObject getJson() {

	    // Configure GSON
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();

	    // Convert to JSON
	    // A list of objects
	    final ArrayList<Object> jobs = getJobs();
	    final String json2 = gson.toJson(jobs);
	    //System.out.println(json2);
		
	    JSONArray jbo = null;
		try {
			jbo = new JSONArray(json2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JSONObject fullJson = new JSONObject();
		try {
			fullJson.put( "success", true);
			fullJson.put( "results", jbo);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	    System.out.println("Yaga Json : " + jbo.toString());
	    System.out.println("Yaga Json : " + fullJson.toString());
	    
		return fullJson;
	}
	

}
