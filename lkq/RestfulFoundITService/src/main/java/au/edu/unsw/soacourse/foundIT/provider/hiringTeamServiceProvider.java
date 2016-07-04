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

import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.hiringTeamImpl;
import au.edu.unsw.soacourse.foundIT.serviceImpl.reviewImpl;;

@Path("hiringTeams")
public class hiringTeamServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public hiringTeamImpl teamImpl = new hiringTeamImpl();

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<hiringTeamBean> getAllHiringTeams() {
		System.out.println("11111111");
		List<hiringTeamBean> bs = new ArrayList<hiringTeamBean>();
		bs = teamImpl.getAllHiringTeams();
		// System.out.println(bs.get(0).getId());
		return bs;

	}
	// @XmlType(propOrder = { "id", "detail", "skill", "experience" })
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTeam(
			// @FormParam("id") String id,
			@FormParam("size") String size 
			/*@FormParam("rId") String rId,
			@FormParam("psw") String psw,
			@FormParam("skill") String skill*/
			) throws IOException {

		hiringTeamBean hiringTeamBean = new hiringTeamBean();
		if (size != null) {
			hiringTeamBean.setSize(size);
		}
		/*if (rId != null) {
			hiringTeamBean.setrId(rId);
		}
		if (psw != null) {
			hiringTeamBean.setPsw(psw);
		}
		if (skill != null) {
			hiringTeamBean.setSkill(skill);
		}*/
		//
		teamImpl.createTeam(hiringTeamBean);
		// TODO: Fix here so that it returns the new book
	}


}
