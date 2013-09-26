package com.as400samplecode;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.as400samplecode.util.Country;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
 
@WebServlet("/CountryInfo")
public class CountryInfo extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    public CountryInfo() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        System.out.println("accessed");
        String countryCode = request.getParameter("countryCode");
        String callback = request.getParameter("callback");
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
 
        Country countryInfo = getInfo(countryCode);
        JsonElement countryObj = gson.toJsonTree(countryInfo);
        if(countryInfo.getName() == null){
            myObj.addProperty("success", false);
        }
        else {
            myObj.addProperty("success", true);
        }
        myObj.add("countryInfo", countryObj);
        myObj.add("countryInfo", countryObj);
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
 
    //Get Country Information
    private Country getInfo(String countryCode) {
 
        Country country = new Country();
 
        country.setCode("UK");
        country.setName("UK");
        country.setContinent("UK");
        country.setRegion("UK");
        country.setLifeExpectancy(3.55);
        country.setGnp(5.99);
            
 
        return country;
 
    }  
 
    public static String getMyStackTrace(Exception e) {
 
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
 
    }
 
 
}