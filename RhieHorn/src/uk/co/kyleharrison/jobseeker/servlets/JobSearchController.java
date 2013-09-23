package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import uk.co.kyleharrison.jobseeker.connectors.CareerJetConnector;
import uk.co.kyleharrison.jobseeker.connectors.IndeedConnector;
import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;
import uk.co.kyleharrison.jobseeker.model.CareerJetPojo;
import uk.co.kyleharrison.jobseeker.model.IndeedPojo;
import uk.co.kyleharrison.jobseeker.stores.jobStore;
import uk.co.kyleharrison.jobseeker.utils.RequestJSON;

import com.careerjet.webservices.api.Client;

@WebServlet("/JobSearchController/*")
public class JobSearchController extends HttpServlet {
private static final long serialVersionUID = 1L;
       
	private IndeedConnector IDC;
	private IndeedPojo indeedP;
	private jobStore _jobStore;
	//private CareerJetConnector CJC;

	public JobSearchController() {
	        super();
	}
	
	@Override
	public void init() {
		_jobStore = new jobStore();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestPath = request.getRequestURI();
		String[] pathComponents = requestPath.split("/");
		JSONObject json =null;
		
		if(pathComponents.length>3){
			if(pathComponents[3].equalsIgnoreCase("careerjet")){
				json = CareerJetSearch();
			}else if(pathComponents[3].equalsIgnoreCase("indeed")){
				try{
					RequestJSON RJS = new RequestJSON(this.IDC, this.indeedP);
					org.json.JSONObject json2 = RJS.Search();
					printOut(response,json2);	

				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.print("Invalid Search");
			}
			if(json!=null){
				printOut(response,json);
			}
			else{
				//printOut(response,"Json = Null");
			}
				
		}
		
		//RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
		//rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
	
	private void printOut(HttpServletResponse response,JSONObject json){
	    if(json!=null) {
	        response.setContentType("text/x-json;charset=UTF-8");           
	        response.setHeader("Cache-Control", "no-cache");
		
			PrintWriter out = null;
			/*
			try {
				out = new PrintWriter(response.getOutputStream());
				json.writeJSONString(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				out.close();
			}
			*/
			
			response.setContentType("application/json");
	
			try {
				out = response.getWriter();
				out.print(json);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private void printOut(HttpServletResponse response,org.json.JSONObject json){
	    if(json!=null) {
	        response.setContentType("text/x-json;charset=UTF-8");           
	        response.setHeader("Cache-Control", "no-cache");
		
			PrintWriter out = null;		
			response.setContentType("application/json");
	
			try {
				out = response.getWriter();
				out.print(json);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private void printOut(HttpServletResponse response,String text){
	    if(text!=null) {
	        response.setContentType("text/html;charset=UTF-8");           
	        response.setHeader("Cache-Control", "no-cache");
		
			PrintWriter out = null;

			try {
				out = response.getWriter();
				out.print(text);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private void IndeedSearch(){
		this.IDC = new IndeedConnector();
		this.IDC.Search();
	}
	
	private JSONObject CareerJetSearch(){
		CareerJetConnector cj = new CareerJetConnector(); 
		Client careerjetClient = cj.getCareerjetClient();
		cj.setParameters("*","Dundee");

		   JSONObject results = (JSONObject) careerjetClient.search(cj.getParameters());
		   
		   System.out.println(results.toJSONString());
		   results = cj.preformSearch(results);
		   cj.verifySearch(results);
		   
		   
		   //Convert to Pojo
		  ArrayList<CareerJetPojo>cjp = cj.getPojoJobs();
		  if(cjp.isEmpty()){
			  System.out.println("Empty List");
		  }else{
			  System.out.println("List populated" +cjp.size());
			  System.out.println(cjp.get(0).getJobTitle());
			  //addArrayList(cjp);
		  }
		  
		  /*
		  try{
			  ArrayList<JobInterface> JOI = cj.getInterfaceJobs();
			  if(JOI.isEmpty()){
				  System.out.println("Empty List");
			  }else{
				  System.out.println("List populated" +JOI.size());
				  System.out.println(JOI.get(0).getJobTitle());
				  //addArrayList(cjp);
			  }
		  }catch(NullPointerException e){
			  
		  }*/
		  
		   System.out.println("Complete");
		   
		   /*
		   try{
			   ArrayList<JobInterface> _jobInterface = cj.getInterfaceJobs();
			   System.out.println("job Interface"+_jobInterface.size());
		   }catch(NullPointerException e){
			   e.printStackTrace();
		   }*/

		   
		   return results;
	}
	
	private void addArrayList(ArrayList<JobInterface> cjp){
		//_jobStore.updateSubList(cjp);
	}

}




