package uk.co.kyleharrison.jobseeker.model;

public class YagaStruct {

	private String [] parameters = new String [14];
	private String [] columns = new String [14];
	private YagaPojo yagaP;
	
	public YagaStruct(YagaPojo yagaP){
		this.yagaP = yagaP;
	}
	
	public String [] getColumns(){
		
		this.columns[0]="jobBoard";
		this.columns[1]="salaryPeriod";
		this.columns[2]="id";
		this.columns[3]="dateFound";
		this.columns[4]="salaryMaxYearly";
		this.columns[5]="title";
		this.columns[6]="salaryMinYearly";
		this.columns[7]="location";
		this.columns[8]="description";
		this.columns[9]="advertiserType";
		this.columns[10]="rawSalary";
		this.columns[11]="url";
		this.columns[12]="advertiserName";
		this.columns[13]="type";
		return columns;
		
	}
	public String [] getParameters(){
		
		this.parameters[0]=yagaP.getJobBoard();
		this.parameters[1]=yagaP.getSalaryPeriod();
		this.parameters[2]=Integer.toString(yagaP.getId());
		this.parameters[3]=yagaP.getDateFound();
		this.parameters[4]=yagaP.getSalaryMaxYearly();
		this.parameters[5]=yagaP.getTitle();
		this.parameters[6]=yagaP.getSalaryMinYearly();
		this.parameters[7]=yagaP.getLocation();
		this.parameters[8]=yagaP.getDescription();
		this.parameters[9]=yagaP.getAdvertiserType();
		this.parameters[10]=yagaP.getRawSalary();
		this.parameters[11]=yagaP.getUrl();
		this.parameters[12]=yagaP.getAdvertiserName();
		this.parameters[13]=yagaP.getType();
		
		return parameters;
	}
	
}
