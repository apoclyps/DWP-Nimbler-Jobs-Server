package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.kyleharrison.jobseeker.connectors.MySQLConnector;

import com.as400samplecode.util.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
 
@WebServlet("/Login")
public class AuthenticationController extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    public AuthenticationController() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        System.out.println("Authentication - User Access");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String callback = request.getParameter("callback");
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
 
        User userInfo = getInfo(email,password);
        JsonElement countryObj = gson.toJsonTree(userInfo);
        
        if(authenticateLogin(email,password)){
            myObj.addProperty("success", true);
        }
        else {
            myObj.addProperty("success", false);
        }
        myObj.add("userInfo", countryObj);
        if(callback != null) {
            out.println(callback + "(" + myObj.toString() + ");");
            System.out.println(callback + "(" + myObj.toString() + ");");
        }
        else {
            out.println(myObj.toString());
            System.out.println(myObj.toString());
        }
        
        out.close();
 
    }
    
    private boolean authenticateLogin(String email, String password){
    	
    	MySQLConnector mySQLCon = new MySQLConnector();
    	String queryString = "SELECT loginID FROM ubiquity.login Where email_address = '"+email+"' AND password = '"+password+"'";
    	
    	if(!(mySQLCon.checkConnection())){
    		System.out.println("connection pool fail");
    		return false;
    	}
    	
    	return mySQLCon.query(queryString);
    }
    
    //Get Country Information
    private User getInfo(String email, String password) {
 
    	User userInfo = new User();
 
    	userInfo.setEmailAddress(email);
    	userInfo.setPassword(password);
            
        return userInfo;
 
    }  
 
    public static String getMyStackTrace(Exception e) {
 
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
 
    }
 
 
}