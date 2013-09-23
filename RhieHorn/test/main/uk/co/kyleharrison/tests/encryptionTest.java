package uk.co.kyleharrison.tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import uk.co.kyleharrison.jobseeker.utils.Encryption;

public class encryptionTest {

	@Test
	public void EncryptionStrings()
	{
		try {
			assertEquals("0a4d55a8d778e5022fab701977c5d840bbc486d0",Encryption.makeSHA1Hash("Hello World"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	}

}
