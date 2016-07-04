package au.edu.unsw.soacourse.foundIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import au.edu.unsw.soacourse.foundIT.dao.ReviewDAO;
import au.edu.unsw.soacourse.foundIT.dao.ReviewerDAO;
import au.edu.unsw.soacourse.foundIT.model.Review;
import au.edu.unsw.soacourse.foundIT.model.Reviewer;

@Path("/reviews")
public class ReviewsResource {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// POST to create a review
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response newReview(
			// @FormParam("id") String id,
			@FormParam("appID") String appID, @FormParam("reviewerID") String reviewerID) throws IOException {
		ReviewDAO dao = new ReviewDAO();
		Review rw = new Review("", appID, reviewerID, "", "");
		dao.createReview(rw);
		return Response.created(uriInfo.getAbsolutePath()).entity(rw).build();
	}
	
	
	// POST to create a review
	@POST
	@Path("byname")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response newReviewByName(
			// @FormParam("id") String id,
			@FormParam("appID") String appID, @FormParam("username") String username) throws IOException {
		ReviewDAO dao = new ReviewDAO();
		ReviewerDAO rwDao = new ReviewerDAO();
		Reviewer rwer = rwDao.getOneReviewerByName(username);
		
		Review rw = new Review("", appID, rwer.getReviewerID(), "", "");
		
		List<Review> rwlist = new ArrayList<Review>();
		rwlist = dao.getAllReviews();
		int count = 0;
		for (int i = 0; i < rwlist.size(); i++) {
			if (rwlist.get(i).getAppID().equals(appID)) {
				if(rwlist.get(i).getReviewerID().equals(rwer.getReviewerID()))
				{
					return Response.ok().entity(rwlist.get(i)).build();
				}
				count++;
			}
		}
		if(count >=2)
			return Response.ok().build();
		
		dao.createReview(rw);
		return Response.created(uriInfo.getAbsolutePath()).entity(rw).build();
	}

	// get all reviews
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response getALLReviews() {
		List<Review> rwlist = new ArrayList<Review>();
		ReviewDAO dao = new ReviewDAO();
		rwlist = dao.getAllReviews();
		return Response.ok(rwlist).build();
	}
	
	// get one review
	@GET
	@Path("{reviewID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response getOneReview(@PathParam("reviewID") String reviewID) {
		ReviewDAO dao = new ReviewDAO();
		Review rw = new Review();
		rw = dao.getOneReview(reviewID);
		if (rw == null) {
			return Response.status(404).build();
		}
		return Response.ok(rw).build();
	}
	
	// get reviews per app
	@GET
	@Path("/appID/{appID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response getReviewsPerApp(@PathParam("appID") String appID) {
		ReviewDAO dao = new ReviewDAO();
		ArrayList<Review> rwlist = new ArrayList<Review>();
		ArrayList<Review> resultlist = new ArrayList<Review>();
		rwlist = dao.getAllReviews();
		for (int i = 0; i < rwlist.size(); i++) {
			if (rwlist.get(i).getAppID().equals(appID)) {
				resultlist.add(rwlist.get(i));
			}
		}
		if (resultlist.size() == 0)
			return Response.status(404).build();
		return Response.ok(resultlist).build();
	}
	
	// get reviews per reviewer
	@GET
	@Path("/reviewerID/{reviewerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getReviewsPerReviewer(@PathParam("reviewerID") String reviewerID) {
		ReviewDAO dao = new ReviewDAO();
		ArrayList<Review> rwlist = new ArrayList<Review>();
		ArrayList<Review> resultlist = new ArrayList<Review>();
		rwlist = dao.getAllReviews();
		for (int i = 0; i < rwlist.size(); i++) {
			if (rwlist.get(i).getReviewerID().equals(reviewerID)) {
				resultlist.add(rwlist.get(i));
			}
		}
		if (resultlist.size() == 0)
			return Response.status(404).build();
		return Response.ok(resultlist).build();
	}
	
}