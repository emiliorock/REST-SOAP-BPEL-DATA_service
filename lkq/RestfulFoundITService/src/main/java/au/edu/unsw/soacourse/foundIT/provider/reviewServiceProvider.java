package au.edu.unsw.soacourse.foundIT.provider;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.reviewImpl;;

@Path("reviews")
public class reviewServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public reviewImpl reviewImpl = new reviewImpl();

	@DELETE
	@Path("{Id}")
	public void delete(@PathParam("Id") String id) {
		// System.out.println("222123123");
		boolean flag = reviewImpl.deleteReview(id);
		if (!flag)
			throw new RuntimeException("DELETE: review with " + id + " not found");

	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<reviewBean> getAllReviews() {
		System.out.println("11111111");
		List<reviewBean> bs = new ArrayList<reviewBean>();
		bs = reviewImpl.getAllReviews();
		// System.out.println(bs.get(0).getId());
		return bs;

	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		// int count = BooksDao.instance.getStore().size();
		return Integer.toString(reviewImpl.getAllReviews().size());
	}

	@GET
	@Path("review/{reviewId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public reviewBean getReview(@PathParam("reviewId") String id) {
		System.out.println("22222222");
		reviewBean b = reviewImpl.getSpecificReview(id);
		if (b == null) {
			throw new RuntimeException("GET: review with " + id + " not found");
		}
		return b;
	}
	
	@GET
	@Path("application/{appId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<reviewBean> getAppReviews(@PathParam("appId") String id) {
		//System.out.println("22222222");
		List<reviewBean> b = reviewImpl.getAppReviews(id);
		if (b == null) {
			throw new RuntimeException("GET: application with " + id + " has no review");
		}
		return b;
	}
	
	@GET
	@Path("reviewer/{uId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<reviewBean> getReviewerReviews(@PathParam("uId") String uId) {
		//System.out.println("22222222");
		List<reviewBean> b = reviewImpl.getReviewerReviews(uId);
		if (b == null) {
			throw new RuntimeException("GET: reviewer with " + uId + " has no review");
		}
		return b;
	}

	// @XmlType(propOrder = { "id", "detail", "skill", "experience" })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newReview(
			// @FormParam("id") String id,
			@FormParam("appId") String appId, 
			@FormParam("uId") String uId,
			@FormParam("comments") String comments,
			@FormParam("decision") String decision) throws IOException {

		reviewBean rev = new reviewBean();
		if (appId != null) {
			rev.setAppId(appId);
		}
		if (uId != null) {
			rev.setuId(uId);
		}
		if (comments != null) {
			rev.setComments(comments);
		}
		if (decision != null) {
			rev.setDecision(decision);
		}
		//
		reviewImpl.createReview(rev);
		// TODO: Fix here so that it returns the new book
	}

	// to be continued......
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createApplication(reviewBean rev) throws Exception {

		return putAndGetResponse(rev);

	}

	private Response putAndGetResponse(reviewBean rev) {
		javax.ws.rs.core.Response res;
		boolean flag = reviewImpl.updateReview(rev);
		if (flag)
			res = javax.ws.rs.core.Response.created(uri.getAbsolutePath()).build();
		else {
			res = javax.ws.rs.core.Response.noContent().build();
		}
		return res;

	}

}
