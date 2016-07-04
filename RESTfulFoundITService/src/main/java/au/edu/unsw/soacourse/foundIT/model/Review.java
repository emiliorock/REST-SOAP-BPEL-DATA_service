package au.edu.unsw.soacourse.foundIT.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Review {
	
	 private String reviewID;
	 private String appID;
	 private String reviewerID;
	 private String comments;
	 private String decision;
	 
	 public Review() {
		 
	 }

	public Review(String reviewID, String appID, String reviewerID, String comments, String decision) {
		this.reviewID = reviewID;
		this.appID = appID;
		this.reviewerID = reviewerID;
		this.comments = comments;
		this.decision = decision;
	}

	public String getReviewID() {
		return reviewID;
	}

	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	  
}