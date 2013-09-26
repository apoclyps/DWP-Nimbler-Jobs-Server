package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.co.kyleharrison.jobseeker.connectors.MySQLConnector;
import uk.co.kyleharrison.jobseeker.model.ApplicationSessionPojo;
import uk.co.kyleharrison.jobseeker.utils.PostcodeUtil;

@WebServlet("/RequestController")
public class RequestController extends HttpServlet {
private static final long serialVersionUID = 1L;
       
	public RequestController() {
	    super();
	 }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestPath = request.getRequestURI();
		String[] pathComponents = requestPath.split("/");
		
		if(pathComponents.length>3){
			if(pathComponents[3].equalsIgnoreCase("JobSearch")){
				
			}else if(pathComponents[3].equalsIgnoreCase("SocialSearch")){
				
			}else if(pathComponents[3].equalsIgnoreCase("Authenticate")){
				
			}else if(pathComponents[3].equalsIgnoreCase("Database")){
				
			}else if(pathComponents[3].equalsIgnoreCase("Login")){
				
			}else if(pathComponents[3].equalsIgnoreCase("Postcode")){
				PostcodeUtil PCU = new PostcodeUtil();
				PCU.Search();
			}else{
				System.out.print("Invalid Search");
			}
			
		}
	
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("accessed");
		
		
		
	}
	
	private void directToHome(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		HttpSession session = req.getSession();
		ApplicationSessionPojo currentUserSession = (ApplicationSessionPojo)session.getAttribute("Session");
		
		System.out.println("Accessed Server");
		
		
		
		try	{
			if(currentUserSession != null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.forward(req, res);
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			rd.forward(req, res);
		}
		}catch (Exception e){
			System.out.print(e.getMessage()+"Home Controller");
		}
	}
	
	private void testMySQL(){
		MySQLConnector MySQLCon = new MySQLConnector();
		try {
			//MySQLCon.selectData();
			/*if(MySQLCon.deleteData("login","loginID")==1){
				System.out.println("Sucessfully Deleted");
			}*/
			
			String [] parameters = {"22334455","K@b.com","99988833","asdas","asdas"};
			MySQLCon.createData("login",parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}