package uk.co.kyleharrison.jobseeker.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;
import uk.co.kyleharrison.jobseeker.model.IndeedPojo;

public class JsonParser {

	private static JsonParser instance =null;
	private IndeedPojo job;
	
	protected JsonParser(){
		//Exists only to defeat instantiation.
	}
	
	public static JsonParser getInstance(){
		if(instance==null){
			instance = new JsonParser();
		}
		return instance;
	}
	
	public void parseResults(String JSONString)
	{
		IndeedPojo currentJob = null;
		
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		try {
			currentJob = mapper.readValue(JSONString, IndeedPojo.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(currentJob!=null)
			System.out.println("Job" + currentJob.getJobTitle());
		}
			
	}
	
	private void createJob()
	{
		JobInterface job = new IndeedPojo();
		
		job.getJobTitle();
	}
	
}
