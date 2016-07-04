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

import au.edu.unsw.soacourse.foundIT.dao.CompanyProfileDAO;
import au.edu.unsw.soacourse.foundIT.model.CompanyProfile;

@Path("companyProfiles")
public class CompanyProfilesResources {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("manager/{managerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCompanyProfile(@PathParam("managerID") String managerID) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		String companyID = cpDAO.managerIDToCompanyID(managerID);
		if (companyID == null) {
			return Response.status(404).build();
		} else {
			CompanyProfile cp = cpDAO.getCompanyProfile(companyID);
			return Response.ok().entity(cp).build();
		}
	}

	@GET
	@Path("company/{companyID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCompanyProfileByCompanyID(@PathParam("companyID") String companyID) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		CompanyProfile cp = cpDAO.getCompanyProfile(companyID);
		if (cp == null) {
			return Response.status(404).build();
		} else {
			return Response.ok().entity(cp).build();
		}
	}

	@GET
	@Path("getCompanyID")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCompanyID(@QueryParam("managerID") String managerID) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		String companyID = cpDAO.managerIDToCompanyID(managerID);
		if (companyID == null) {
			return Response.status(404).build();
		} else {
			return Response.ok().entity(companyID).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createCompanyProfile(@FormParam("managerID") String managerID, @FormParam("name") String name,
			@FormParam("url") String url, @FormParam("intro") String intro, @FormParam("img") String img) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		String companyID = cpDAO.managerIDToCompanyID(managerID);
		CompanyProfile cp;
		if(companyID == null){
			cp = new CompanyProfile("", managerID, name, url, intro, img);
			cpDAO.createCompanyProfile(cp);
		}else{
			cp = cpDAO.getCompanyProfile(companyID);
		}
		return Response.created(uriInfo.getAbsolutePath()).entity(cp).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateCompanyProfile(@FormParam("managerID") String managerID, @FormParam("name") String name,
			@FormParam("url") String url, @FormParam("intro") String intro, @FormParam("img") String img) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		String companyID = cpDAO.managerIDToCompanyID(managerID);
		if (companyID == null) {
			return Response.status(404).build();
		}
		CompanyProfile cp = new CompanyProfile(companyID, managerID, name, url, intro, img);
		cpDAO.updateCompanyProfile(cp);
		return Response.ok().entity(cp).build();
	}

	@DELETE
	@Path("{managerID}")
	public Response deleteCompanyProfie(@PathParam("managerID") String managerID) {
		CompanyProfileDAO cpDAO = new CompanyProfileDAO();
		String companyID = cpDAO.managerIDToCompanyID(managerID);
		if (companyID == null) {
			return Response.status(404).build();
		}
		cpDAO.deleteCompanyProfile(companyID);
		return Response.ok().build();
	}

}
