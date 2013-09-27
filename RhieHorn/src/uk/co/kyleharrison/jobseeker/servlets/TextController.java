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

/**
 * Nimbler Server - Text Controller 
 * Purpose: Sends Texts to Users regarding notifications
 * @author KYle Harrison
 * @version 1.0 27/09/2013
 */
@WebServlet("/TextController/*")
public class TextController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String API_KEY = "2fc8bc99e7fda13de7600bff406255e6830ea10e";
	
    /**
     * Init Text Controller
     */
    public TextController() {
        super();
    }

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	/**
	 * checkCredit
	 * Returns the remaining credit from the account as a long
	 */
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
	
	/**
	 * SendText
	 * Sends a text to the user
	 */
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
