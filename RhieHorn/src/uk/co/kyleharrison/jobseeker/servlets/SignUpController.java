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
 
@WebServlet("/SignUp")
public class SignUpController extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    private MySQLConnector mySQLCon;
 
    public SignUpController() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        System.out.println("Accessed Sign Up Controller");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("passwordConfirmation");
        
        String title = request.getParameter("title");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String postcode = request.getParameter("postcode");
        String callback = request.getParameter("callback");
        

        /*
        Enumeration<String> en=request.getParameterNames();
        while(en.hasMoreElements()){
        String str=en.nextElement().toString();
        System.out.println("parameter name-->"+str);
        System.out.println("parameter value-->"+request.getParameter(str));
        } 
        */
         
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
 
        User userInfo = new User(email,password,password_confirmation,title,firstname,surname,postcode);
        JsonElement signUpObj = gson.toJsonTree(userInfo);
        
        	
        if(authenticatUserName(email)){
            myObj.addProperty("success", false);
        }
        else {
            
        	addUser(email,password);
        	
        	myObj.addProperty("success", true);
        }
        
        //Closing Connection
        mySQLCon.close();
        
        myObj.add("userInfo", signUpObj);
        if(callback != null) {
            out.println(callback + "(" + myObj.toString() + ");");
            System.out.println(callback + "(" + myObj.toString() + ");");
            System.out.println(myObj.toString());
        }
        else {
            out.println(myObj.toString());
            System.out.println(myObj.toString());
        }
        
        out.close();
 
    }
    
    private boolean connect(){
    	mySQLCon = new MySQLConnector();
    	return mySQLCon.checkConnection();
    }
    
    private boolean authenticatUserName(String email){
    	
    	if(connect()){
        	String queryString = "SELECT loginID FROM ubiquity.login Where email_address = '"+email+"'";
        	
        	return mySQLCon.query(queryString);
    	}
    	return false;
    }
    
    private boolean addUser(String email, String password){
    	
    	String parameter [] = new String [2];
    	parameter[0] = email;
    	parameter[1] = password;
    	
    	
    	mySQLCon.createStoredProcedure("login", parameter);
    	
    	return true;
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