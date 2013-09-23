package uk.co.kyleharrison.jobseeker.model;
import java.util.Date;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;


public class CareerJetPojo implements JobInterface {

	private String site;
	private char salary_type;
	private String title;
	private String locations;
	private String description;
	private String company;
	private String salary_currency_code; 
	private String salary;
	private int salary_min;
	private int salary_max;
	private Date date;
	private String url;
	
	public CareerJetPojo(){
		super();
	}
	
	public CareerJetPojo(String site, char salary_type, String title,
			String locations, String description, String company,
			String salary_currency_code, String salary, int salary_min,
			int salary_max, Date date, String url) {
		super();
		this.site = site;
		this.salary_type = salary_type;
		this.title = title;
		this.locations = locations;
		this.description = description;
		this.company = company;
		this.salary_currency_code = salary_currency_code;
		this.salary = salary;
		this.salary_min = salary_min;
		this.salary_max = salary_max;
		this.date = date;
		this.url = url;
	}
	
	//Getters and Setters
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public char getSalary_type() {
		return salary_type;
	}
	public void setSalary_type(char salary_type) {
		this.salary_type = salary_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getlocations() {
		return locations;
	}
	public void setlocations(String locations) {
		this.locations = locations;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSalary_currency_code() {
		return salary_currency_code;
	}
	public void setSalary_currency_code(String salary_currency_code) {
		this.salary_currency_code = salary_currency_code;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public int getSalary_min() {
		return salary_min;
	}
	public void setSalary_min(int salary_min) {
		this.salary_min = salary_min;
	}
	public int getSalary_max() {
		return salary_max;
	}
	public void setSalary_max(int salary_max) {
		this.salary_max = salary_max;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getJobTitle() {
		return this.title;
	}

	@Override
	public String getSnippet() {

		return null;
	}

	@Override
	public String getURL() {

		return null;
	}
	
}
