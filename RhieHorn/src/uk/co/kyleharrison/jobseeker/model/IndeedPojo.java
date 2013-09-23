package uk.co.kyleharrison.jobseeker.model;

import java.util.Date;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;

public class IndeedPojo implements JobInterface {

	private String jobtitle;
	private String company;
	private String city;
	private String state;
	private String country;
	private String formattedLocation;
	private String source;
	private Date date;
	private String snippet;
	private String url;
	private double latitude;
	private double longitude;
	private String jobkey;
	private boolean sponsored;
	private boolean expired;
	private String formattedLocationFull;
	private String formattedRelativeTime;
	
	// Constructor
	public IndeedPojo(){
		this.jobtitle = null;
		this.company= null;
		this.city= null;
		this.state= null;
		this.country= null;
		this.formattedLocation= null;
		this.source= null;
		this.date= null;
		this.snippet= null;
		this.url= null;
		this.latitude= 0.00;
		this.longitude= 0.00;
		this.jobkey= null;
		this.sponsored= false;
		this.expired= false;
		this.formattedLocationFull= null;
		this.formattedRelativeTime= null;
	}
	
	public IndeedPojo(String jobtitle,String company,String city,String state,String country,String formattedLocation,
			String source,Date date,String snippet,	double latitude,double longitude,String jobkey,
			boolean sponsored,boolean expired,String formattedLocationFull,String formattedRelativeTime)
	{
		this.jobtitle = jobtitle;
		this.company= null;
		this.city= null;
		this.state= null;
		this.country= null;
		this.formattedLocation= null;
		this.source= null;
		this.date= null;
		this.snippet= null;
		this.url= null;
		this.latitude= 0.00;
		this.longitude= 0.00;
		this.jobkey= null;
		this.sponsored= false;
		this.expired= false;
		this.formattedLocationFull= null;
		this.formattedRelativeTime= null;
	}
	
	// Getters and Setters
	public String getJobTitle() {
		return this.jobtitle;
	}
	public void setJobTitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	public String getCompany() {
		return this.company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFormattedLocation() {
		return this.formattedLocation;
	}
	public void setFormattedLocation(String formattedLocation) {
		this.formattedLocation = formattedLocation;
	}
	public String getSource() {
		return this.source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSnippet() {
		return this.snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getURL() {
		return this.url;
	}
	public void setURL(String url) {
		this.url = url;
	}
	public double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getJobkey() {
		return this.jobkey;
	}
	public void setJobkey(String jobkey) {
		this.jobkey = jobkey;
	}
	public boolean isSponsored() {
		return this.sponsored;
	}
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	public boolean isExpired() {
		return this.expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public String getFormattedLocationfull() {
		return this.formattedLocationFull;
	}
	public void setFormattedLocationfull(String formattedLocationfull) {
		this.formattedLocationFull = formattedLocationfull;
	}
	public String getFormattedRelativeTime() {
		return this.formattedRelativeTime;
	}
	public void setFormattedRelativeTime(String formattedRelativeTime) {
		this.formattedRelativeTime = formattedRelativeTime;
	}

}
