package au.edu.unsw.soacourse.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import au.edu.unsw.soacourse.beans.CompanyProfile;
import au.edu.unsw.soacourse.beans.JobPosting;
import au.edu.unsw.soacourse.beans.Reviewer;

public class ManagerLogic {
	static public void getJobs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// System.out.println(sb.toString());
			// convert to object
			Gson gson = new Gson();
			JsonElement je = gson.fromJson(sb.toString(), JsonElement.class);
			JsonObject jo = je.getAsJsonObject();

			String companyID = "";
			if (jo.has("companyID"))
				companyID = jo.get("companyID").getAsString();

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/jobs";
			REST_URI += "/company/" + companyID;

			WebClient client = WebClient.create(REST_URI);
			String s = client.get(String.class);
			// System.out.println(s);

			// write the json
			out.write(s);
			out.close();
		} catch (Exception e) {
			out.write("failed");
			e.printStackTrace();
			out.close();
		}

	}

	static public void addJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			JobPosting job = gson.fromJson(sb.toString(), JobPosting.class);
			// check

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/jobs";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("companyID", job.getCompanyID());
			form.param("companyName", job.getCompanyName());
			form.param("salaryRate", job.getSalaryRate());
			form.param("positionType", job.getPositionType());
			form.param("location", job.getLocation());
			form.param("description", job.getDescription());
			form.param("status", job.getStatus());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);

			client.back(true);
			boolean ctreate = true;
			out.print(ctreate);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();

	}

	static public void updateJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			JobPosting job = gson.fromJson(sb.toString(), JobPosting.class);
			// check
			boolean update = true;
			String jobID = job.getJobID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/jobs/";
			WebClient client = WebClient.create(REST_URI);
			Form form = new Form();
			form.param("jobID", job.getJobID());
			form.param("companyID", job.getCompanyID());
			form.param("companyName", job.getCompanyName());
			form.param("salaryRate", job.getSalaryRate());
			form.param("positionType", job.getPositionType());
			form.param("location", job.getLocation());
			form.param("description", job.getDescription());
			form.param("status", job.getStatus());

			client.path("/update").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.put(form);
			out.print(update);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();
	}

	static public void deleteJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			JobPosting job = gson.fromJson(sb.toString(), JobPosting.class);
			// check
			boolean delete = true;
			String jobID = job.getJobID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/jobs";
			WebClient client = WebClient.create(REST_URI);

			client.path("/" + jobID).delete();
			out.print(delete);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();
	}

	static public void getReviewers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		CompanyProfile cp = (CompanyProfile) session.getAttribute("companyProfileSession");
		String companyID = cp.getCompanyID();

		String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviewers";
		REST_URI += "/company/" + companyID;

		WebClient client = WebClient.create(REST_URI);
		String s = client.get(String.class);
		// System.out.println(s);

		// write the json
		out.write(s);
		out.close();
	}

	static public void addReviewer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.print("add");
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}

			// convert to object
			Reviewer rw = gson.fromJson(sb.toString(), Reviewer.class);
			// check

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviewers";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("companyID", rw.getCompanyID());
			form.param("username", rw.getUsername());
			form.param("password", rw.getPassword());
			form.param("professionalSkills", rw.getProfessionalSkills());
			form.param("status", rw.getStatus());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);

			client.back(true);
			boolean ctreate = true;
			out.print(ctreate);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();

	}

	static public void updateReviewer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			Reviewer rw = gson.fromJson(sb.toString(), Reviewer.class);
			// check
			boolean update = true;
			String rwID = rw.getReviewerID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviewers/";
			WebClient client = WebClient.create(REST_URI);
			Form form = new Form();
			form.param("reviewerID", rw.getReviewerID());
			form.param("companyID", rw.getCompanyID());
			form.param("username", rw.getUsername());
			form.param("password", rw.getPassword());
			form.param("professionalSkills", rw.getProfessionalSkills());
			form.param("status", rw.getStatus());

			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.put(form);
			out.print(update);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();
	}

	static public void deleteReviewer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			Reviewer rw = gson.fromJson(sb.toString(), Reviewer.class);
			// check
			boolean delete = true;
			String rwID = rw.getReviewerID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviewers";
			WebClient client = WebClient.create(REST_URI);

			client.path("/" + rwID).delete();
			out.print(delete);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}

		// return succeed
		// System.out.println("checked email");
		out.close();
	}

}
