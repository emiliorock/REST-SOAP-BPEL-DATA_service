package au.edu.unsw.soacourse.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobPosting {
	
	private String jobID;
	private String companyID;
	private String companyName;
	private String salaryRate;
	private String positionType;
	private String location;
	private String description;
	private String status;
	
	public JobPosting() {
		
	}
	
	public JobPosting(String jobID, String companyID, String companyName, String salaryRate, String positionType, String location, String description, String status) {
		this.jobID = jobID;
		this.companyID = companyID;
		this.companyName = companyName;
		this.salaryRate = salaryRate;
		this.positionType = positionType;
		this.location = location;
		this.description = description;
		this.status = status;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSalaryRate() {
		return salaryRate;
	}

	public void setSalaryRate(String salaryRate) {
		this.salaryRate = salaryRate;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}