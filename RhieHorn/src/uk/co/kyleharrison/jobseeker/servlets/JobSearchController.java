package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.jobseeker.connectors.CareerJetConnector;
import uk.co.kyleharrison.jobseeker.connectors.IndeedConnector;
import uk.co.kyleharrison.jobseeker.connectors.YagaJobsConnector;
import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;
import uk.co.kyleharrison.jobseeker.model.CareerJetPojo;
import uk.co.kyleharrison.jobseeker.model.IndeedPojo;
import uk.co.kyleharrison.jobseeker.model.YagaPojo;
import uk.co.kyleharrison.jobseeker.stores.jobStore;
import uk.co.kyleharrison.jobseeker.utils.RequestJSON;

import com.careerjet.webservices.api.Client;

@WebServlet("/JobSearchController/*")
public class JobSearchController extends HttpServlet {
private static final long serialVersionUID = 1L;
       
	private IndeedConnector IDC;
	private IndeedPojo indeedP;
	private jobStore _jobStore;
	private YagaJobsConnector YJC;
	private YagaPojo YagaP;
	//private CareerJetConnector CJC;
	public int offset = 600;

	public JobSearchController() {
	        super();
	}
	
	@Override
	public void init() {
		_jobStore = new jobStore();
		YJC = new YagaJobsConnector();
		YJC.offset = offset;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Accessed - Job Search Controller");
		String requestPath = request.getRequestURI();
		String[] pathComponents = requestPath.split("/");
		
        String title = request.getParameter("title");
        String password = request.getParameter("location");
        String postcode = request.getParameter("postcode");
        String callback = request.getParameter("callback");
        
        System.out.println("Title : "+title);

		System.out.println(requestPath);
		
		if(pathComponents.length>3){
			parseComponents(request, response, pathComponents);
		}
		else
		{
			System.out.println("No Parameters");
		}
		
	}
	
	private void parseComponents(HttpServletRequest request, HttpServletResponse response,String[] pathComponents){
		JSONObject json =null;
		
		if(pathComponents[3].equalsIgnoreCase("getjobs")){
			System.out.println("Getting Jobs : Json");
			
			org.json.JSONObject jbo= null;
			
			try{
				jbo = YJC.getJson();
				//System.out.println(YJC.getJson().toString());
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
	        String callback = "jsonpCallback";

			if(jbo!=null){
				printOut(response,(callback + "(" + jbo.toString() + ");"));
				System.out.println(callback + "(" + jbo.toString() + ");");
			}else{
				System.out.println("Object = null");
			}
			
		}else if (pathComponents[3].equalsIgnoreCase("careerjet")){
			json = CareerJetSearch();
		}else if(pathComponents[3].equalsIgnoreCase("indeed")){
			try{
				RequestJSON RJS = new RequestJSON(this.IDC, this.indeedP);
				org.json.JSONObject json2 = RJS.Search("http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=java&l=dundee&sort=" +
						"&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=uk&chnl=" +
						"&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2");
				
				printOut(response,json2);	

			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(pathComponents[3].equalsIgnoreCase("yaga")) {
			try{
				System.out.println("Yaga Job Search");
				YJC = new YagaJobsConnector();
				RequestJSON RJS = new RequestJSON(this.YJC, this.YagaP);
				//org.json.JSONObject json2 = RJS.SearchYaga("http://yagajobs.co.uk/api/vacancies.json/search?&api_key=92c07bf9353c26cd36b41d91f3190fde&location=Dundee&offset=0");
				
				// Dundee / Perth / Forfar / Cupar / Edinburgh
				
				String [] locations = {"dundee","perth","forfar","cupar","edinburgh"};
				
				System.out.println("OFf Set ="+offset);
				offset += 200;
				YJC.offset =offset;
				JSONArray jsonA = null;
				
					for(int j=0;j<locations.length;j++)
					{
						try{
							YJC.location=locations[j];
							
							jsonA = null;
							for(int i=0; i<10;i++){
								jsonA = RJS.getJSONArrayFromUrl(YJC.buildURI());
								
								System.out.println("Testing JSONA"+ jsonA.get(0));
								org.json.JSONObject JSO = (org.json.JSONObject) jsonA.get(0);
								//System.out.println("Title : " +JSO.get("jobBoard"));
								if(jsonA!=null){
									//System.out.println("mapping to pojo");
									try{
										YJC.mapToPojo(jsonA);
									}catch(NullPointerException e){
										e.printStackTrace();
									}	
								}		
								YJC.offset +=20;
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
					

				System.out.println(jsonA);
				printOut(response,jsonA);	

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
		
		System.out.println("OFf Set ="+offset);
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
	
	private void printOut(HttpServletResponse response,JSONArray jsonA){
	    if(jsonA!=null) {
	        response.setContentType("text/x-json;charset=UTF-8");           
	        response.setHeader("Cache-Control", "no-cache");
		
			PrintWriter out = null;		
			response.setContentType("application/json");
	
			try {
				out = response.getWriter();
				out.print(jsonA);
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
	
	private void YagaSearch(){
		this.YJC = new YagaJobsConnector();
		this.YJC.Search();
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




