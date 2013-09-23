package uk.co.kyleharrison.jobseeker.connectors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;
import uk.co.kyleharrison.jobseeker.model.CareerJetPojo;

import com.careerjet.webservices.api.Client;


public class CareerJetConnector {

	private Client careerjetClient;
	private Map<String, String> parameters;
	private ArrayList<CareerJetPojo> PojoJobs;
	private ArrayList<JobInterface> InterfaceJobs;
	
	public CareerJetConnector(){
		careerjetClient = new Client("en_GB");
		PojoJobs = new ArrayList<CareerJetPojo>();
	}
	
	public ArrayList<CareerJetPojo> getPojoJobs() {
		return PojoJobs;
	}

	public void setPojoJobs(ArrayList<CareerJetPojo> pojoJobs) {
		PojoJobs = pojoJobs;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(String keywords, String location){
		
		this.parameters = new HashMap<String, String>();
			parameters.put("keywords", keywords);
			parameters.put("location", location);
			parameters.put("start_num", "0");
	}
	
	public static JSONObject searchClientjet(){
		return null;
		
	}
	
	public Client getCareerjetClient() {
		return careerjetClient;
	}

	public void setCareerjetClient(Client careerjetClient) {
		this.careerjetClient = careerjetClient;
	}
	
	public JSONObject preformSearch(JSONObject results){
		// A list of jobs is returned
		   if (results.get("type").equals("JOBS")) {
		       JSONArray jobs = (JSONArray) results.get("jobs");
		       System.out.println("Number of results:" + results.get("hits"));
		       int index = 0;
		       try{
				       while( index < jobs.size()) {
				           JSONObject job = (JSONObject) jobs.get(index);
				           System.out.println("URL         :" + job.get("url"));
				           System.out.println("TITLE       :" + job.get("title"));
				           System.out.println("COMPANY     :" + job.get("company"));
				           System.out.println("SALARY      :" + job.get("salary"));
				           System.out.println("DATE        :" + job.get("date"));
				           System.out.println("DESCRIPTION :" + job.get("description"));
				           System.out.println("SITE        :" + job.get("site"));
				           System.out.println("LOCATIONS   :" + job.get("locations"));
				           System.out.println("");
				           index++;
				     
				           mapToPojo(jobs,index);
				          // mapToInterface(job,index);
			       }

		       }catch(NullPointerException NPE){
		    	   System.out.println("Null Pointer Exception " +NPE.getMessage());
		       }catch(Exception e){
		    	   System.out.println("No Results : " +e.getMessage());
		       }
		   }
		  return results;
	}
	
	public void mapToPojo(JSONArray jobs, int index)	{
		
		 //Mapping Object to Pojo
        ObjectMapper mapper = new ObjectMapper();
        try {
			CareerJetPojo cjp = mapper.readValue(jobs.get(index).toString(), CareerJetPojo.class);
			PojoJobs.add(mapper.readValue(jobs.get(index).toString(), CareerJetPojo.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mapToInterface(JSONObject job, int index)	{
		
		System.out.println("Job "+index + " " + job.toString());
		
		/*
		JobInterface jb = new CareerJetPojo();
        ObjectMapper mapper = new ObjectMapper();
        
		try {
			jb = mapper.readValue(job.toString(), CareerJetPojo.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			this.InterfaceJobs.add(jb);
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
	}
	
	public boolean verifySearch(JSONObject results){
		   // The location was amiguous. Suggestions are returned.
		   // Add the location_id to the query to resolve the ambiguity.
		   if (results.get("type").equals("LOCATIONS")) {
		       System.out.println("Narrow down your location ");
		       System.out.println("Please specify a location");
		       JSONArray solvelocations = (JSONArray) results.get("solveLocations");
		       int index = 0;
		       while(index < solvelocations.size()) {
		    	   //Handle incorrect search
		           JSONObject location = (JSONObject) solvelocations.get(index);
		           System.out.println("NAME        :" + location.get("name"));
		           System.out.println("LOCATION ID :" + location.get("location_id"));
		           index++;
		       }
		   }

		   // An error occured. An error message is returned.
		   if (results.get("type").equals("ERROR")) {
		       System.out.println("An error occurred whilst processing the search query");
		       System.out.println("Error message    :" + results.get("ERROR"));
		       return false;
		   }
		   return true;
	}

	public ArrayList<JobInterface> getInterfaceJobs() {
		return InterfaceJobs;
	}

	public void setInterfaceJobs(ArrayList<JobInterface> interfaceJobs) {
		InterfaceJobs = interfaceJobs;
	}
	
}
