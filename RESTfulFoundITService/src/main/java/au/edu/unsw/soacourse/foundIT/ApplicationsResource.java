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
import au.edu.unsw.soacourse.foundIT.dao.ApplicationDAO;
import au.edu.unsw.soacourse.foundIT.model.Application;

@Path("/apps")
public class ApplicationsResource {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// POST to create a application
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response newApplication(
			// @FormParam("id") String id,
			@FormParam("jobID") String jobID, @FormParam("profileID") String profileID,
			@FormParam("coverLetter") String coverLetter, @FormParam("status") String status) throws IOException {
		ApplicationDAO dao = new ApplicationDAO();
		Application app = new Application("", jobID, profileID, coverLetter, status);
		dao.createApplication(app);
		return Response.created(uriInfo.getAbsolutePath()).entity(app).build();
	}

	// get all applications
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getALLApplications() {
		List<Application> applist = new ArrayList<Application>();
		ApplicationDAO dao = new ApplicationDAO();
		applist = dao.getAllApplications();
		return Response.ok(applist).build();
	}

	// get one application
	@GET
	@Path("{appID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getOneApplication(@PathParam("appID") String appID) {
		ApplicationDAO dao = new ApplicationDAO();
		Application app = new Application();
		app = dao.getOneApplication(appID);
		if (app == null) {
			return Response.status(404).build();
		}
		return Response.ok(app).build();
	}

	// get applications per job
	@GET
	@Path("/jobID/{jobID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getApplicationsPerJob(@PathParam("jobID") String jobID) {
		ApplicationDAO dao = new ApplicationDAO();
		ArrayList<Application> applist = new ArrayList<Application>();
		ArrayList<Application> resultlist = new ArrayList<Application>();
		applist = dao.getAllApplications();
		for (int i = 0; i < applist.size(); i++) {
			if (applist.get(i).getJobID().equals(jobID)) {
				resultlist.add(applist.get(i));
			}
		}
		if (resultlist.size() == 0)
			return Response.status(404).build();
		return Response.ok(resultlist).build();
	}
	
	
	@GET
	@Path("/profileID/{profileID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getApplicationsPerProfile(@PathParam("profileID") String profileID) {
		ApplicationDAO dao = new ApplicationDAO();
		ArrayList<Application> applist = new ArrayList<Application>();
		ArrayList<Application> resultlist = new ArrayList<Application>();
		applist = dao.getAllApplications();
		for (int i = 0; i < applist.size(); i++) {
			if (applist.get(i).getProfileID().equals(profileID)) {
				resultlist.add(applist.get(i));
			}
		}
		if (resultlist.size() == 0)
			return Response.status(404).build();
		return Response.ok().entity(resultlist).build();
	}

	// delete a application
	@DELETE
	@Path("{appID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteApplication(@PathParam("appID") String appID) {
		ApplicationDAO dao = new ApplicationDAO();
		boolean ok = dao.deleteApplication(appID);
		if (ok == false)
			return Response.status(404).build();
		return Response.status(200).build();
	}

	// update application by user
	// update details
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateApplication(@FormParam("appID") String appID, @FormParam("jobID") String jobID,
			@FormParam("profileID") String profileID, @FormParam("coverLetter") String coverLetter,
			@FormParam("status") String status) throws TransformerException {
		Application app = new Application(appID, jobID, profileID, coverLetter, status);
		return putAndGetResponse(app);
	}
	
	private Response putAndGetResponse(Application app) throws TransformerException {
		Response res;
		ApplicationDAO dao = new ApplicationDAO();
		if (dao.updateApplication(app) != null) {
			res = Response.ok(app).build();
		} else {
			res = Response.status(404).build();
		}
		return res;
	}

}