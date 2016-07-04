package au.edu.unsw.soacourse.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Application {
	
	private String appID;
	private String jobID;
	private String profileID;
	private String coverLetter;
	private String status;
	
	public Application () {
		
	}

	public Application(String appID, String jobID, String profileID, String coverLetter, String status) {
		this.appID = appID;
		this.jobID = jobID;
		this.profileID = profileID;
		this.coverLetter = coverLetter;
		this.status = status;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
}