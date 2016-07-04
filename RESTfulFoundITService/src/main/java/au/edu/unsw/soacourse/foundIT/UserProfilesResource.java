package au.edu.unsw.soacourse.foundIT;

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

import au.edu.unsw.soacourse.foundIT.dao.UserProfileDAO;
import au.edu.unsw.soacourse.foundIT.model.UserProfile;

@Path("userProfiles")
public class UserProfilesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("user/{userID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProfile(@PathParam("userID") String id) {
		UserProfileDAO upDAO = new UserProfileDAO();
		UserProfile up = upDAO.getProfileByUserID(id);
		if (up == null) {
			return Response.status(404).build();
		}
		return Response.ok().entity(up).build();
	}

	@GET
	@Path("profile/{profileID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProfileByProfileID(@PathParam("profileID") String id) {
		UserProfileDAO upDAO = new UserProfileDAO();
		UserProfile up = upDAO.getProfileByProfileID(id);
		if (up == null) {
			return Response.status(404).build();
		}
		return Response.ok().entity(up).build();
	}

	@GET
	@Path("getProfileID")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProfileID(@QueryParam("userID") String userID) {
		UserProfileDAO upDAO = new UserProfileDAO();
		String profileID = upDAO.userIDToProfileID(userID);
		if (profileID == null) {
			return Response.status(404).build();
		} else {
			return Response.ok().entity(profileID).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createProfifle(@FormParam("userID") String userID, @FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname, @FormParam("email") String email, @FormParam("phone") String phone,
			@FormParam("driverLicense") String driverLicense, @FormParam("address") String address,
			@FormParam("professionalSkills") String professionalSkills, @FormParam("experience") String experience,
			@FormParam("img") String img) {
		UserProfileDAO upDAO = new UserProfileDAO();
		UserProfile up = upDAO.getProfileByUserID(userID);
		if (up == null) {
			up = new UserProfile("", userID, firstname, lastname, email, phone, driverLicense, address,
					professionalSkills, experience, img);
			up = upDAO.createUserProfile(up);
		}
		return Response.created(uriInfo.getAbsolutePath()).entity(up).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateProfile(@FormParam("userID") String userID, @FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname, @FormParam("email") String email, @FormParam("phone") String phone,
			@FormParam("driverLicense") String driverLicense, @FormParam("address") String address,
			@FormParam("professionalSkills") String professionalSkills, @FormParam("experience") String experience,
			@FormParam("img") String img) {
		UserProfileDAO upDAO = new UserProfileDAO();
		String profileID = upDAO.userIDToProfileID(userID);
		if (profileID == null) {
			return Response.status(404).build();
		}
		UserProfile up = new UserProfile(profileID, userID, firstname, lastname, email, phone, driverLicense, address,
				professionalSkills, experience, img);
		upDAO.updateProfile(up);
		return Response.ok().entity(up).build();
	}

	@DELETE
	@Path("{userID}")
	public Response deleteProfile(@PathParam("userID") String userID) {
		UserProfileDAO upDAO = new UserProfileDAO();
		String profileID = upDAO.userIDToProfileID(userID);
		if (profileID == null) {
			return Response.status(404).build();
		}
		upDAO.deleteProfile(profileID);
		return Response.ok().build();
	}
}
