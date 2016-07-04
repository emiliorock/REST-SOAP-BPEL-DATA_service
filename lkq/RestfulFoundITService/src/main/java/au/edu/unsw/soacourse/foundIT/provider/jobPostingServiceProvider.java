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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.foundIT.modeler.jobPostingBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.jobPostingImpl;

@Path("jobPostings")
public class jobPostingServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public jobPostingImpl jobPostingImpl = new jobPostingImpl();

	@DELETE
	@Path("{Id}")
	public void deletePosting(@PathParam("Id") String id) {
		// System.out.println("222123123");
		boolean flag = jobPostingImpl.deletePosting(id);
		if (!flag)
			throw new RuntimeException("DELETE: posting with " + id + " not found");

	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<jobPostingBean> getAllPostings() {
		System.out.println("11111111");
		List<jobPostingBean> bs = new ArrayList<jobPostingBean>();
		bs = jobPostingImpl.getAllPostings();
		// System.out.println(bs.get(0).getId());
		return bs;

	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		// int count = BooksDao.instance.getStore().size();
		return Integer.toString(jobPostingImpl.getAllPostings().size());
	}
	
//    <posting>
//    <id>4136</id>
//    <comLink>sfdafsd</comLink>
//    <salaryRate>asdfas</salaryRate>
//    <positionType>asdfasdfs</positionType>
//    <location>asdfsa</location>
//    <description>safdas</description>
//    <status>asdfas</status>
//</posting>
	@GET
	@Path("/searchJob")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<jobPostingBean> getUsers(
		@QueryParam("salaryRate") String salaryRate,
		@QueryParam("positionType") String positionType,
		@QueryParam("location") String location,
		@QueryParam("status") String status) {
		//System.out.println(location+"22222222ddd");
		
		if(salaryRate==null)
			salaryRate="";
		if(positionType==null)
			positionType="";
		if(location==null)
			location="";
		if(status==null)
			status="";
		List<jobPostingBean> result=jobPostingImpl.
				searchJobByKeyWord(salaryRate, positionType, location, status);
		return result;



	}


	@GET
	@Path("{Id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public jobPostingBean getJobPosting(@PathParam("Id") String id) {
		//System.out.println("22222222");
		jobPostingBean b = jobPostingImpl.getSpecificPosting(id);
		if (b == null) {
			throw new RuntimeException("GET: posting with " + id + " not found");
		}
		return b;
	}

	// @XmlType(propOrder = { "id", "detail", "skill", "experience" })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newPosting(
			// @FormParam("id") String id,
			@FormParam("comLink") String link, 
			@FormParam("salaryRate") String salaryRate,
			@FormParam("positionType") String positionType, 
			@FormParam("location") String location,
			@FormParam("description") String description, 
			@FormParam("status") String status) throws IOException {

		jobPostingBean jobPosting = new jobPostingBean();
		if (link != null) {
			jobPosting.setComLink(link);
		}
		if (salaryRate != null) {
			jobPosting.setSalaryRate(salaryRate);
		}
		if (positionType != null) {
			jobPosting.setPositionType(positionType);
		}
		if (location != null) {
			jobPosting.setLocation(location);
		}
		if (description != null) {
			jobPosting.setDescription(description);
		}
		if (status != null) {
			jobPosting.setStatus(status);
		}
		//
		//jobPostingImpl.createPosting(jobPosting);
		String resourceUrl = "http://localhost:8080/RestfulFoundITService/wlr/userProfiles"
				+ "/" + jobPostingImpl.createPosting(jobPosting);
		return resourceUrl;
		// TODO: Fix here so that it returns the new book
	}

	// to be continued......
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createPosting(jobPostingBean posting) throws Exception {

		return putAndGetResponse(posting);

	}

	private Response putAndGetResponse(jobPostingBean posting) {
		javax.ws.rs.core.Response res;
		boolean flag = jobPostingImpl.updatePosting(posting);
		if (flag)
			res = javax.ws.rs.core.Response.created(uri.getAbsolutePath()).build();
		else {
			res = javax.ws.rs.core.Response.noContent().build();
		}
		return res;

	}

}
