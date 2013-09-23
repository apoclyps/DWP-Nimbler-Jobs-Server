package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.jobseeker.connectors.FacebookConnector;
import uk.co.kyleharrison.jobseeker.connectors.TwitterConnector;

@WebServlet("/SocialMediaController/*")
public class SocialMediaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SocialMediaController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestPath = request.getRequestURI();
		String[] pathComponents = requestPath.split("/");

		PrintWriter out = new PrintWriter(response.getOutputStream());
		response.setContentType("text/html");
		out.println(requestPath);
		out.println("<b>"+pathComponents[3]+"</b>");
			
		if(pathComponents[3].equalsIgnoreCase("twitter")){
			TwitterSearch();
		}else if(pathComponents[3].equalsIgnoreCase("facebook")){
			FacebookSearch();
		}else{
			out.print("Invalid Search");
		}
		
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private void FacebookSearch(){
		FacebookConnector FBC = new FacebookConnector();
		FBC.search();
	}
	
	private void TwitterSearch(){
		TwitterConnector TWC = new TwitterConnector();
		TWC.search();
	}

}
