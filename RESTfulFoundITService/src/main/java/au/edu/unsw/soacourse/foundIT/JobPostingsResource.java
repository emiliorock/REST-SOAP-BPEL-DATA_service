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
import au.edu.unsw.soacourse.foundIT.dao.JobPostingDAO;
import au.edu.unsw.soacourse.foundIT.model.Application;
import au.edu.unsw.soacourse.foundIT.model.JobPosting;

@Path("/jobs")
public class JobPostingsResource {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// POST to create a job
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response newJob(
			// @FormParam("id") String id,
			@FormParam("companyID") String companyID, @FormParam("companyName") String companyName,
			@FormParam("salaryRate") String salaryRate, @FormParam("positionType") String positionType,
			@FormParam("location") String location, @FormParam("description") String description,
			@FormParam("status") String status) throws IOException {
		JobPostingDAO dao = new JobPostingDAO();
		JobPosting job = new JobPosting("", companyID, companyName, salaryRate, positionType, location, description,
				status);
		dao.createJob(job);
		return Response.created(uriInfo.getAbsolutePath()).entity(job).build();
	}

	// get all jobs
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getALLJobs() {
		List<JobPosting> joblist = new ArrayList<JobPosting>();
		JobPostingDAO dao = new JobPostingDAO();
		joblist = dao.getAllJobs();
		return Response.ok(joblist).build();
	}

	// get one job
	@GET
	@Path("{jobID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getOneJob(@PathParam("jobID") String jobID) {
		JobPostingDAO dao = new JobPostingDAO();
		JobPosting job = new JobPosting();
		job = dao.getOneJob(jobID);
		if (job == null) {
			return Response.status(404).build();
		}
		return Response.ok(job).build();
	}
	
	@GET
	@Path("company/{companyID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML  })
	public Response getOneReview(@PathParam("companyID") String companyID) {
		JobPostingDAO dao = new JobPostingDAO();
		ArrayList<JobPosting> joblist = new ArrayList<JobPosting>();
		ArrayList<JobPosting> resultlist = new ArrayList<JobPosting>();
		joblist = dao.getAllJobs();
		for (int i = 0; i < joblist.size(); i++) {
			if (joblist.get(i).getCompanyID().equals(companyID)) {
				resultlist.add(joblist.get(i));
			}
		}
		if (resultlist.size() == 0)
			return Response.status(404).build();
		return Response.ok().entity(resultlist).build();
	}

	// search for jobs
	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response searchJobs(@QueryParam("companyName") String companyName,
			@QueryParam("salaryRate1") String salaryRate1, @QueryParam("salaryRate2") String salaryRate2,
			@QueryParam("positionType") String positionType, @QueryParam("location") String location,
			@QueryParam("description") String description, @QueryParam("status") String status) {
		JobPostingDAO dao = new JobPostingDAO();
		
		ArrayList<JobPosting> joblist = new ArrayList<JobPosting>();
		ArrayList<JobPosting> resultlist = new ArrayList<JobPosting>();
		joblist = dao.getAllJobs();

		

		for (int i = 0; i < joblist.size(); i++) {
			boolean addFlag = true;
			//System.out.println("round"+i);
			if (!companyName.equals("")&& !joblist.get(i).getCompanyName().toLowerCase().contains(companyName.toLowerCase())) {
				addFlag = false;
			}
		//	System.out.println(addFlag);
			
			// salary interval
			if(salaryRate1.equals("") && salaryRate2.equals("")){
				;
			}else{
				int sr1 = 0;
				int sr2 = 1000;
				if(!salaryRate1.equals(""))
					sr1 = Integer.parseInt(salaryRate1);
				if(!salaryRate2.equals(""))
					sr2 = Integer.parseInt(salaryRate2);
				int s = Integer.parseInt(joblist.get(i).getSalaryRate());				
				if (!(s >= sr1 && s <= sr2)) {
					addFlag = false;
				}
			}
		//	System.out.println(addFlag);
			
			if (!positionType.equals("")&&!joblist.get(i).getPositionType().toLowerCase().contains(positionType.toLowerCase())) {
				addFlag = false;
			}	
		//	System.out.println(addFlag);
			
			if (!location.equals("")&&!joblist.get(i).getLocation().toLowerCase().contains(location.toLowerCase())) {
				addFlag = false;
			}
		//	System.out.println(addFlag);
			
			if (!description.equals("")&&!joblist.get(i).getDescription().toLowerCase().contains(description.toLowerCase())) {
				addFlag = false;
			}
		//	System.out.println(addFlag);
			
			if (!status.equals("")&&!joblist.get(i).getStatus().toLowerCase().contains(status.toLowerCase())) {
				addFlag = false;
			}
		//	System.out.println(addFlag);
			
			if(addFlag)
				resultlist.add(joblist.get(i));
		}
		return Response.ok().entity(resultlist).build();
	}

	// update a job
	// once a job status changed from open to close
	// the app involved should change their status too
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateJob(@FormParam("jobID") String jobID, @FormParam("companyID") String companyID,
			@FormParam("companyName") String companyName, @FormParam("salaryRate") String salaryRate,
			@FormParam("positionType") String positionType, @FormParam("location") String location,
			@FormParam("description") String description, @FormParam("status") String status)
			throws TransformerException {
		JobPosting job = new JobPosting(jobID, companyID, companyName, salaryRate, positionType, location, description,
				status);
		return putAndGetResponse(job);
	}

	// delete a job
	@DELETE
	@Path("{jobID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteJob(@PathParam("jobID") String jobID) {
		JobPostingDAO dao = new JobPostingDAO();
		boolean ok = dao.deleteJob(jobID);
		if (ok == false)
			return Response.status(404).build();
		return Response.status(200).build();
	}

	private Response putAndGetResponse(JobPosting job) throws TransformerException {
		Response res;
		ApplicationDAO dao = new ApplicationDAO();
		JobPostingDAO jdao = new JobPostingDAO();
		ArrayList<Application> alist = new ArrayList<Application>();
		alist = dao.getAllApplications();
		int flag = 0;
		for (int i = 0; i < alist.size(); i++) {
			if (alist.get(i).getJobID().equals(job.getJobID())) {
				flag = 1;
			}
		}
		if (flag == 0) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		} else {
			res = Response.ok(job).build();
		}
		jdao.updateJob(job);
		return res;
	}

}