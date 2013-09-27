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
import uk.co.kyleharrison.jobseeker.model.CareerJetPojo;
import uk.co.kyleharrison.jobseeker.model.IndeedPojo;
import uk.co.kyleharrison.jobseeker.model.YagaPojo;
import uk.co.kyleharrison.jobseeker.utils.URLRequestJSONUtil;

import com.careerjet.webservices.api.Client;

/**
 * @author Kyle
 *
 */
@WebServlet("/JobSearchController/*")
public class JobSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IndeedConnector IDC;
	private IndeedPojo indeedP;
	private YagaJobsConnector YJC;
	private YagaPojo YagaP;
	//private jobStore _jobStore;
	private CareerJetConnector CJ;
	public int offset = 0;

	/**
	 * 
	 */
	public JobSearchController() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {
		//_jobStore = new jobStore();
		YagaInit();
		IndeedInit();
		//CareerJetInit();
		YJC.offset = offset;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Accessed - Job Search Controller");
		String requestPath = request.getRequestURI();
		String[] pathComponents = requestPath.split("/");

		if (pathComponents.length > 3) {
			parseComponents(request, response, pathComponents);
		} else {
			System.out.println("No Parameters");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/index.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Calls the appropriate function whenever a parameter is passed in the URI
	 * @param request
	 * @param response
	 * @param pathComponents
	 */
	private void parseComponents(HttpServletRequest request,
			HttpServletResponse response, String[] pathComponents) {

		if (pathComponents[3].equalsIgnoreCase("getjobs")) {
			requestJobs(request,response);
		} else if (pathComponents[3].equalsIgnoreCase("careerjet")) {
			JSONObject json = CareerJetSearch();
			
			if(json!=null){
				printOut(response,json);
			}
		} else if (pathComponents[3].equalsIgnoreCase("indeed")) {
			IndeedSearch(response);
		} else if (pathComponents[3].equalsIgnoreCase("yaga")) {
			YagaSearch(response);
		} else {
			System.out.print("Invalid Search");
		}
		
	}
	
	/**
	 * Prints out JSON
	 * @param request
	 * @param response
	 */
	private void requestJobs(HttpServletRequest request,
			HttpServletResponse response){
		org.json.JSONObject jbo = null;
		String[] parameters = new String[4];
		
		try {
			parameters[0] = request.getParameter("title");
			parameters[1] = request.getParameter("location");
			parameters[2] = request.getParameter("postcode");
		} catch (NullPointerException e) {
			System.out.println("Parameter List not initialised");
		}

		try {
			jbo = YJC.getJson(parameters);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		String callback = "jsonpCallback";
		if (jbo != null) {
			printOut(response, (callback + "(" + jbo.toString() + ");"));
			System.out.println(callback + "(" + jbo.toString() + ");");
		} else {
			System.out.println("Object = null");
		}
	}

	/**
	 * Prints out JSON
	 * @param response
	 * @param json
	 */
	private void printOut(HttpServletResponse response, JSONObject json) {
		if (json != null) {
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

	/**
	 * Prints out JSON
	 * @param response
	 * @param jsonA
	 */
	private void printOut(HttpServletResponse response, JSONArray jsonA) {
		if (jsonA != null) {
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

	/**
	 * Prints out JSON
	 * @param response
	 * @param json
	 */
	private void printOut(HttpServletResponse response, org.json.JSONObject json) {
		if (json != null) {
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

	/**
	 * Prints out JSON
	 * @param response
	 * @param text
	 */
	private void printOut(HttpServletResponse response, String text) {
		if (text != null) {
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

	/**
	 * Init Yaga 
	 */
	private void YagaInit() {
		this.YJC = new YagaJobsConnector();
		//this.YJC.Search();
	}
	
	/**
	 * Searches Yaga Jobs for all jobs and stored them in mysql
	 * @param response
	 */
	private void YagaSearch(HttpServletResponse response) {
		try {
			System.out.println("Yaga Job Search");
			YJC = new YagaJobsConnector();
			URLRequestJSONUtil RJS = new URLRequestJSONUtil(this.YJC,
					this.YagaP);

			// Dundee / Perth / Forfar / Cupar / Edinburgh
			String[] locations = { "dundee", "perth", "forfar", "cupar",
					"edinburgh" };

			offset += 200;
			YJC.offset = offset;
			JSONArray jsonA = null;

			for (int j = 0; j < locations.length; j++) {
				try {
					YJC.location = locations[j];

					jsonA = null;
					for (int i = 0; i < 10; i++) {
						jsonA = RJS.getJSONArrayFromUrl(YJC.buildURI());

						System.out.println("Testing JSONA" + jsonA.get(0));

						if (jsonA != null) {
							try {
								YJC.mapToPojo(jsonA);
							} catch (NullPointerException e) {
								e.printStackTrace();
							}
						}
						YJC.offset += 20;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			System.out.println(jsonA);
			printOut(response, jsonA);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Init Indeed Connector once
	 */
	private void IndeedInit() {
		this.IDC = new IndeedConnector();
		//this.IDC.Search();
	}
	
	/**
	 * Searches Indeed jobs for all jobs in dundee
	 * @param response
	 */
	private void IndeedSearch(HttpServletResponse response){
		try {
			URLRequestJSONUtil RJS = new URLRequestJSONUtil(this.IDC,
					this.indeedP);
			org.json.JSONObject json2 = RJS
					.Search("http://api.indeed.com/ads/apisearch?publisher=8188725749639977&q=*&l=dundee&sort="
							+ "&format=json&radius=&st=&jt=&start=&limit=&fromage=&filter=&latlong=1&co=uk&chnl="
							+ "&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2");

			printOut(response, json2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void CareerJetInit(){
		CJ = new CareerJetConnector();
	}
	
	/**
	 * Searches CareerJet for all jobs in Dundee
	 * @return
	 */
	private JSONObject CareerJetSearch() {
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
	
	/*
	 * To Do - Code for in Memory Sorting
	private void addArrayList(ArrayList<JobInterface> cjp) {
		_jobStore.updateSubList(cjp);
	}
	*/

}
