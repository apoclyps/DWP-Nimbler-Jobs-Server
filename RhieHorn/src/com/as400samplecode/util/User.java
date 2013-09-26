package com.as400samplecode.util;

import uk.co.kyleharrison.jobseeker.utils.Encryption;

public class User {

	private String email;
	private String password;
	private String passwordConfirmation;
	private String encrypted_password;
	private String title;
	private String firstname;
	private String surname;
	private String postcode;
	
	public User(){
		
	}
	
	public User(String email, String password, String password_conformation,String title,
			String firstName, String surname, String postcode) {
		super();
		this.email = email;
		this.password = password;
		this.passwordConfirmation = password_conformation;
		this.title = title;
		this.firstname = firstName;
		this.surname = surname;
		this.postcode = postcode;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmailAddress() {
		return email;
	}

	public void setEmailAddress(String emailAddress) {
		this.email = emailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_conformation() {
		return passwordConfirmation;
	}

	public void setPassword_conformation(String password_conformation) {
		this.passwordConfirmation = password_conformation;
	}

	public String getEncrypted_password() {
		return encrypted_password;
	}

	public void setEncrypted_password(String encrypted_password) {
		
		this.encrypted_password = Encryption.makeSHA1Hash(encrypted_password);;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
}
