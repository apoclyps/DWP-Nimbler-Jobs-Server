import java.util.ArrayList;

import org.json.simple.JSONObject;

import uk.co.kyleharrison.jobseeker.connectors.CareerJetConnector;
import uk.co.kyleharrison.jobseeker.model.CareerJetPojo;

import com.careerjet.webservices.api.Client;

public class APITestMain {

public static void main (String [] arguments){
		
		CareerJetConnector cj = new CareerJetConnector(); 
		Client careerjetClient = cj.getCareerjetClient();
		cj.setParameters("*","Dundee");

		   JSONObject results = (JSONObject) careerjetClient.search(cj.getParameters());
		   
		   System.out.println(results.toJSONString());
		   results = cj.preformSearch(results);
		   cj.verifySearch(results);
		   
		   ArrayList<CareerJetPojo>cjp = cj.getPojoJobs();
		  if(cjp.isEmpty()){
			  System.out.println("Empty List");
		  }else{
			  System.out.println("List populated" +cjp.size());
		  }

		
		   System.out.println("Complete");
	}
	
}
