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

import au.edu.unsw.soacourse.foundIT.modeler.userProfileBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.userProfileImpl;

@Path("userProfiles")
public class userProfileServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public userProfileImpl userProfileImpl = new userProfileImpl();

	@DELETE
	@Path("{profileId}")
	public String deleteProfile(@PathParam("profileId") String id) {
		// System.out.println("222123123");
		boolean flag = userProfileImpl.deleteProfile(id);
		if (!flag)
			throw new RuntimeException("DELETE: profile with " + id
					+ " not found");
		return "success";

	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<userProfileBean> getAllProfiles() {
		System.out.println("11111111");
		List<userProfileBean> bs = new ArrayList<userProfileBean>();
		bs = userProfileImpl.getAllUserProfiles();
		System.out.println(bs.size());
		return bs;

	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		// int count = BooksDao.instance.getStore().size();
		return Integer.toString(userProfileImpl.getAllUserProfiles().size());
	}

	@GET
	@Path("{profileId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public userProfileBean getProfile(@PathParam("profileId") String id) {
		// System.out.println("22222222");
		userProfileBean b = userProfileImpl.getSpecificProfile(id);
		if (b == null) {
			throw new RuntimeException("GET: profile with " + id + " not found");
		}
		return b;
	}

	// @XmlType(propOrder = { "id", "detail", "skill", "experience" })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_PLAIN })
	public String newProfile(
			// @FormParam("id") String id,
			@FormParam("detail") String detail,
			@FormParam("skill") String skill,
			@FormParam("experience") String experience) throws IOException {

		userProfileBean userProfile = new userProfileBean();
		if (detail != null) {
			userProfile.setDetail(detail);
		}
		if (skill != null) {
			userProfile.setSkill(skill);
		}
		if (experience != null) {
			userProfile.setExperience(experience);
		}
		String resourceUrl = "http://localhost:8080/RestfulFoundITService/wlr/userProfiles"
				+ "/" + userProfileImpl.createProfile(userProfile);
		return resourceUrl;
	}

	// to be continued......
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createProfile(userProfileBean profile) throws Exception {

		return putAndGetResponse(profile);

	}

	private Response putAndGetResponse(userProfileBean profile) {
		javax.ws.rs.core.Response res;
		boolean flag = userProfileImpl.updataProfile(profile);
		if (flag)
			res = javax.ws.rs.core.Response.created(uri.getAbsolutePath())
					.build();
		else {
			res = javax.ws.rs.core.Response.noContent().build();
		}
		return res;

	}

}
