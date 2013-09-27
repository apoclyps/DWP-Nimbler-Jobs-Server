package uk.co.kyleharrison.jobseeker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	
/*
	public static void main(String [] arguments){
		String data="1";
		try {
			data = makeSHA1Hash("Hello World");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("data = "+data);
		
	}
	*/
	
	    public static String makeSHA1Hash(String input)
	    {
	    		String hexStr = "";
	            MessageDigest md;
				try {
					md = MessageDigest.getInstance("SHA1");
		            md.reset();
		            byte[] buffer = input.getBytes();
		            md.update(buffer);
		            byte[] digest = md.digest();
	

		            for (int i = 0; i < digest.length; i++) {
		                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
		            }
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
	            
	            return hexStr;
	    }

}
