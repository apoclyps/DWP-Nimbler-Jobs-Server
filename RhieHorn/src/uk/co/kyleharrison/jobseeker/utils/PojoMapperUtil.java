package uk.co.kyleharrison.jobseeker.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.kyleharrison.jobseeker.model.IndeedPojo;

public class PojoMapperUtil {

	private static PojoMapperUtil instance = null;
	//private IndeedPojo job;

	protected PojoMapperUtil() {

	}

	public static PojoMapperUtil getInstance() {
		if (instance == null) {
			instance = new PojoMapperUtil();
		}
		return instance;
	}

	public void parseResults(String JSONString) {
		IndeedPojo currentJob = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			currentJob = mapper.readValue(JSONString, IndeedPojo.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (currentJob != null)
				System.out.println("Job" + currentJob.getJobTitle());
		}
	}

	/*
	 * To Do
	private void createJob() {
		JobInterface job = new IndeedPojo();
		job.getJobTitle();
	}
	*/

}
