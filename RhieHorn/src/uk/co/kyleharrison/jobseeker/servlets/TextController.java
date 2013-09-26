package uk.co.kyleharrison.jobseeker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clockworksms.ClockWorkSmsService;
import com.clockworksms.ClockworkException;
import com.clockworksms.ClockworkSmsResult;
import com.clockworksms.SMS;

@WebServlet("/TextController/*")
public class TextController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String API_KEY = "2fc8bc99e7fda13de7600bff406255e6830ea10e";
	
    public TextController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	public void checkCredit(){
		ClockWorkSmsService clockWorkSmsService;
		try {
			clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			Long credit = clockWorkSmsService.checkCredit();
			System.out.println("Credit Remaining : "+credit);
		} catch (ClockworkException e) {
			e.printStackTrace();
		}         
	}
	
	public void sendText(){
		try
	      {
	         ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
	         SMS sms = new SMS("447707285085", "Hello World");        
	         
	         ClockworkSmsResult result = clockWorkSmsService.send(sms);
	 
	         if(result.isSuccess())
	         {
	            System.out.println("Sent with ID: " + result.getId());
	         }
	         else
	         {
	            System.out.println("Error: " + result.getErrorMessage());
	         }
	      }
	      catch (ClockworkException e)
	      {
	         e.printStackTrace();
	      }
	}


}
