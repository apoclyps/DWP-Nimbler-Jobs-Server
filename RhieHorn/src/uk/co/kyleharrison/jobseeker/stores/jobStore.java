package uk.co.kyleharrison.jobseeker.stores;

import java.util.ArrayList;
import java.util.List;

import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;

public class jobStore {
	private List<List<JobInterface>> jobList;
	
	public jobStore(){		
		//System.out.println("List Size = "+jobList.size());
		//System.out.println(""+jobList.subList(0, 0).size());
		this.jobList = new ArrayList<>();
	}
	
	public List<List<JobInterface>> getJobList() {
		return this.jobList;
	}

	public void setJobList(List<List<JobInterface>> jobList) {
		this.jobList = jobList;
	}
	
	public void addSubList(List<JobInterface> subList){
		this.jobList.add(subList);
	}
	
	public void removeSubList(List<JobInterface> subList){
		this.jobList.remove(subList);
	}
	
	public int getListSize(){
		return this.jobList.size();
	}
	
	public int getSubListSize(int index){
		return this.jobList.subList(index, index).size();
	}
	
	public void updateSubList(ArrayList<JobInterface> arrayList){
		//this.jobList.subList(index, index).addAll(index, c)
	}

}
