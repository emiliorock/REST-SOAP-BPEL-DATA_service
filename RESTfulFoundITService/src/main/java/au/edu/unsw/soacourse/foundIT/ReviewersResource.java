package au.edu.unsw.soacourse.foundIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;
import au.edu.unsw.soacourse.foundIT.dao.ReviewerDAO;
import au.edu.unsw.soacourse.foundIT.model.Reviewer;

@Path("/reviewers")
public class ReviewersResource {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// POST to create a review
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Reviewer newReviewer(
			// @FormParam("id") String id,
			@FormParam("companyID") String companyID, @FormParam("username") String username,
			@FormParam("password") String password, @FormParam("professionalSkills") String professionalSkills,
			@FormParam("status") String status) throws IOException {
		ReviewerDAO dao = new ReviewerDAO();
		Reviewer rw = new Reviewer("", companyID, username, password, professionalSkills, status);
		dao.createReviewer(rw);
		return rw;
	}



	// get one reviewer
	@GET
	@Path("reviewer/{reviewerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getOneReview(@PathParam("reviewerID") String reviewerID) {
		ReviewerDAO dao = new ReviewerDAO();
		Reviewer rw = new Reviewer();
		rw = dao.getOneReviewer(reviewerID);
		if (rw == null) {
			return Response.status(404).build();
		}
		System.out.println(reviewerID);
		return Response.ok().entity(rw).build();
	}
	
	
	@GET
	@Path("validate")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response validate(@QueryParam("username") String username, @QueryParam ("password") String password) {
		ReviewerDAO dao = new ReviewerDAO();
		Reviewer result = null;
		ArrayList<Reviewer> rwlist = new ArrayList<Reviewer>();
		rwlist = dao.getAllReviewers();
		for (int i = 0; i < rwlist.size(); i++) {
			if (rwlist.get(i).getUsername().equals(username)) {
				result = rwlist.get(i);
				break;
			}
		}
		if(result == null){
			return Response.status(404).build();
		}else{
			return Response.ok().entity(result).build();
		}
	}
	
	// get all reviews
	@GET
	@Path("/getAllReviewers")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getALLReviewers() {
		List<Reviewer> rwlist = new ArrayList<Reviewer>();
		ReviewerDAO dao = new ReviewerDAO();
		rwlist = dao.getAllReviewers();
		return Response.ok().entity(rwlist).build();
	}

	// get reviewers per company
	@GET
	@Path("company/{companyID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response getReviewersPerCompany(@PathParam("companyID") String companyID) {
		ReviewerDAO dao = new ReviewerDAO();
		ArrayList<Reviewer> rwlist = new ArrayList<Reviewer>();
		ArrayList<Reviewer> resultlist = new ArrayList<Reviewer>();
		rwlist = dao.getAllReviewers();
		for (int i = 0; i < rwlist.size(); i++) {
			if (rwlist.get(i).getCompanyID().equals(companyID)) {
				resultlist.add(rwlist.get(i));
			}
		}
		return Response.ok().entity(resultlist).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response updateReviewer(
			@FormParam("reviewerID") String reviewerID, @FormParam("companyID") String companyID,
			@FormParam("username") String username,
			@FormParam("password") String password, @FormParam("professionalSkills") String professionalSkills,
			@FormParam("status") String status) {
		ReviewerDAO dao = new ReviewerDAO();		
		Reviewer rw = new Reviewer(reviewerID, companyID, username, password, professionalSkills, status);
		try {
			dao.updateReviewer(rw);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(rw).build();
	}
	
	@DELETE
	@Path("{reviewerID}")
	public Response deleteReviewer(@PathParam("reviewerID") String reviewerID){
		ReviewerDAO dao = new ReviewerDAO();
		if(dao.findByID(reviewerID) == null){
			return Response.status(404).build();
		}
		dao.deleteReview(reviewerID);
		return Response.ok().build();
	}
}