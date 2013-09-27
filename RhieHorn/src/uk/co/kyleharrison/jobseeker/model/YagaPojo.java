package uk.co.kyleharrison.jobseeker.model;

import java.util.Date;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;

public class YagaPojo implements JobInterface {

	private int id;
	private String advertiserName;
	private String advertiserType;
	private String salaryMinYearly;
	private String salaryMaxYearly;
	private String salaryPeriod;
	private String location;
	private String type;
	private String hours;
	private String title;
	private String jobBoard;
	private String dateFound;
	private String description;
	private String reference;
	private String rawSalary;
	private String url;

	public YagaPojo(){
		
	}
	
	public YagaPojo(int id, String advertiserName, String advertiserType,
			String salaryMinYearly, String salaryMaxYearly,
			String salaryPeriod, String location, String title,
			String jobBoard, String dateFound, String description,
			String reference, String rawSalary, String url) {
		super();
		this.id = id;
		this.advertiserName = advertiserName;
		this.advertiserType = advertiserType;
		this.salaryMinYearly = salaryMinYearly;
		this.salaryMaxYearly = salaryMaxYearly;
		this.salaryPeriod = salaryPeriod;
		this.location = location;
		this.title = title;
		this.jobBoard = jobBoard;
		this.dateFound = dateFound;
		this.description = description;
		this.reference = reference;
		this.rawSalary = rawSalary;
		this.url = url;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getAdvertiserType() {
		return advertiserType;
	}

	public void setAdvertiserType(String advertiserType) {
		this.advertiserType = advertiserType;
	}

	public String getSalaryMinYearly() {
		return salaryMinYearly;
	}

	public void setSalaryMinYearly(String salaryMinYearly) {
		this.salaryMinYearly = salaryMinYearly;
	}

	public String getSalaryMaxYearly() {
		return salaryMaxYearly;
	}

	public void setSalaryMaxYearly(String salaryMaxYearly) {
		this.salaryMaxYearly = salaryMaxYearly;
	}

	public String getSalaryPeriod() {
		return salaryPeriod;
	}

	public void setSalaryPeriod(String salaryPeriod) {
		this.salaryPeriod = salaryPeriod;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJobBoard() {
		return jobBoard;
	}

	public void setJobBoard(String jobBoard) {
		this.jobBoard = jobBoard;
	}

	public String getDateFound() {
		return dateFound;
	}

	public void setDateFound(String dateFound) {
		this.dateFound = dateFound;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getRawSalary() {
		return rawSalary;
	}

	public void setRawSalary(String rawSalary) {
		this.rawSalary = rawSalary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getJobTitle() {
		return null;
	}
	@Override
	public Date getDate() {
		return null;
	}
	@Override
	public String getSnippet() {
		return null;
	}
	@Override
	public String getURL() {
		return null;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	
	


}
