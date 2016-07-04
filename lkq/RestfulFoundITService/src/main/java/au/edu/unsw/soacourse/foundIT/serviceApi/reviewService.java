package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;;

public interface reviewService {
	
	String createReview(reviewBean review);
	
	boolean deleteReview(String reviewId);
	
	List<reviewBean> getAllReviews();
	
	boolean updateReview(reviewBean review);
	
	reviewBean getSpecificReview(String reviewId);
	
	List<reviewBean> getAppReviews(String appId);
	
	List<reviewBean> getReviewerReviews(String uId);

}