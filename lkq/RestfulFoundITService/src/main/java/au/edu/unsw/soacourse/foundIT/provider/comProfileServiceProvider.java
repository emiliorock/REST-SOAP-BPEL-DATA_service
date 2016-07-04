package au.edu.unsw.soacourse.foundIT.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.foundIT.modeler.companyProfileBean;
import au.edu.unsw.soacourse.foundIT.modeler.userProfileBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.comProfileImpl;


@Path("comProfiles")
public class comProfileServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public comProfileImpl comProfileImpl=new comProfileImpl();
	
	@DELETE
	@Path("{profileId}")
	public void deleteProfile(@PathParam("profileId") String id) {
		//System.out.println("222123123");
		boolean flag=comProfileImpl.deleteProfile(id);
		if(!flag)
			throw new RuntimeException("DELETE: profile with " + id +  " not found");
	}
	
	@GET
	@Path("all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<companyProfileBean> getAllProfiles() {

		List<companyProfileBean> bs = new ArrayList<companyProfileBean>();
		bs=comProfileImpl.getAllComProfiles();
		return bs; 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		//int count = BooksDao.instance.getStore().size();
		return Integer.toString(comProfileImpl.getAllComProfiles().size());
	}
	
	@GET
	@Path("{profileId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public companyProfileBean getProfile(@PathParam("profileId") String id) {
		companyProfileBean b = comProfileImpl.getSpecificProfile(id);
		if(b==null){
			throw new RuntimeException("GET: profile with " + id +  " not found");
		}
		return b;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newProfile(
			//@FormParam("id") String id,
			@FormParam("detail") String detail,
			@FormParam("address") String address,
			@FormParam("phone") String phone
	) throws IOException {
		

		companyProfileBean comProfile = new companyProfileBean();
		if (detail!=null){
			comProfile.setDetail(detail);
		}
		if(address!=null)
		{
			comProfile.setAddress(address);
		}
		if(phone!=null)
		{
			comProfile.setPhone(phone);
		}
		comProfileImpl.createProfile(comProfile);
	}
}
