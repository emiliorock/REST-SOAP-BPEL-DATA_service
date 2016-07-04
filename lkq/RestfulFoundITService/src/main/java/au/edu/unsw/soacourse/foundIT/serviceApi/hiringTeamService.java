package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;;

public interface hiringTeamService {
	
	String createTeam (hiringTeamBean team);
	
	//boolean deleteReview(String reviewId);
	
	List<hiringTeamBean> getAllHiringTeams();
	
	/*boolean updateReview(reviewBean review);
	
	reviewBean getSpecificReview(String reviewId);
	
	List<reviewBean> getAppReviews(String appId);
	
	List<reviewBean> getReviewerReviews(String uId);*/

}