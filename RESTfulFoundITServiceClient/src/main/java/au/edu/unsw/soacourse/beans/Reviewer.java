package au.edu.unsw.soacourse.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reviewer{
	
	private String reviewerID;
	private String companyID;
	private String username;
	private String password;
	private String professionalSkills;
	private String status;
	
	public Reviewer() {
		
	}

	public Reviewer(String reviewerID, String companyID, String username, String password, String professionalSkills, String status) {
		this.reviewerID = reviewerID;
		this.companyID = companyID;
		this.username = username;
		this.password = password;
		this.professionalSkills = professionalSkills;
		this.status = status;
	}

	public String getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfessionalSkills() {
		return professionalSkills;
	}

	public void setProfessionalSkills(String professionalSkills) {
		this.professionalSkills = professionalSkills;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}