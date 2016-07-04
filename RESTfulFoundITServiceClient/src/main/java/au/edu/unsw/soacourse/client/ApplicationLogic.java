package au.edu.unsw.soacourse.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import au.edu.unsw.soacourse.beans.Application;
import au.edu.unsw.soacourse.beans.UserProfile;
import autocheck.*;

public class ApplicationLogic {
	static public void getAppsByJobID(HttpServletRequest request, HttpServletResponse response)
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

			String jobID = "";
			if (jo.has("jobID"))
				jobID = jo.get("jobID").getAsString();

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps";
			REST_URI += "/jobID/" + jobID;

			WebClient client = WebClient.create(REST_URI);
			String s = client.get(String.class);
			// System.out.println(s);

			// write to json

			out.write(s);
			out.close();
		} catch (Exception e) {
			out.write("failed");
			e.printStackTrace();
			out.close();
		}

	}
	
	static public void getAppsByAppID(HttpServletRequest request, HttpServletResponse response)
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

			String appID = "";
			if (jo.has("appID"))
				appID = jo.get("appID").getAsString();

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps/";
			REST_URI += appID;

			WebClient client = WebClient.create(REST_URI);
			String s = client.get(String.class);
			// System.out.println(s);

			// write to json

			out.write(s);
			out.close();
		} catch (Exception e) {
			out.write("failed");
			e.printStackTrace();
			out.close();
		}

	}

	static public void getApps(HttpServletRequest request, HttpServletResponse response)
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

			String profileID = "";
			if (jo.has("profileID"))
				profileID = jo.get("profileID").getAsString();

			// System.out.println(profileID);
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps";
			REST_URI += "/profileID/" + profileID;

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

	static public void deleteApps(HttpServletRequest request, HttpServletResponse response)
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
			Application app = gson.fromJson(sb.toString(), Application.class);
			// check
			boolean delete = true;
			String appID = app.getAppID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps";
			WebClient client = WebClient.create(REST_URI);

			client.path("/" + appID).delete();
			out.print(delete);
			// close reader
			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}
		out.close();
	}

	static public void createApp(HttpServletRequest request, HttpServletResponse response)
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
			Application app = gson.fromJson(sb.toString(), Application.class);
			// check

			boolean create = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps";
			WebClient client = WebClient.create(REST_URI);

			Form form = new Form();
			form.param("coverLetter", app.getCoverLetter());
			form.param("jobID", app.getJobID());
			form.param("profileID", app.getProfileID());
			form.param("status", app.getStatus());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);
			out.print(create);

			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}
		out.close();
	}

	static public void updateApp(HttpServletRequest request, HttpServletResponse response)
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
			Application app = gson.fromJson(sb.toString(), Application.class);
			// check

			boolean upd = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/apps";
			WebClient client = WebClient.create(REST_URI);

			String status = app.getStatus();
			if(status.equals("accept"))
				status="accepted";
			else if(status.equals("reject"))
				status="rejected";
			else if (status.equals("shortlist"))
				status = "shortlisted";
			else if (status.equals("non-shortlist"))
				status = "non-shortlisted";
			
			Form form = new Form();
			form.param("jobID", app.getJobID());
			form.param("coverLetter", app.getCoverLetter());
			form.param("appID", app.getAppID());
			form.param("profileID", app.getProfileID());
			form.param("status", status);
			client.path("/update").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.put(form);
			out.print(upd);

			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}
		out.close();
	}

	static public void autocheck(HttpServletRequest request, HttpServletResponse response)
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
			Application app = gson.fromJson(sb.toString(), Application.class);
			// check

			String status = app.getStatus();
			if (status == null || status.equals("") || status.equals("applied")) {
				String profileID = app.getProfileID();
				String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
				REST_URI += "/profile/" + profileID;
				WebClient client = WebClient.create(REST_URI);
				String s = client.get(String.class);
				UserProfile up = gson.fromJson(s, UserProfile.class);
				String firstname = "";
				if (up.getFirstname() != null)
					firstname = up.getFirstname();
				String lastname = "";
				if (up.getLastname() != null)
					lastname = up.getLastname();

				String fullname = firstname + lastname;

				String dl = "";
				if (up.getDriverLicense() != null)
					dl = up.getDriverLicense();

				String address = "";
				if (up.getAddress() != null)
					address = up.getAddress();

				AutoCheckService service = new AutoCheckService(
						new URL("http://localhost:6060/ode/deployment/bundles/BPEL_AutoCheck/autoCheckArtifacts.wsdl"));
				AutoCheck port = service.getAutoCheckPort();
				AutoCheckRequest req = new autocheck.ObjectFactory().createAutoCheckRequest();
				req.setAddress(address);
				req.setDl(dl);
				req.setFullName(fullname);
				AutoCheckResponse resp = port.process(req);
				String result = resp.getOutput();
	
				String REST_URI2 = "http://localhost:8080/RESTfulFoundITService/apps";
				WebClient client2 = WebClient.create(REST_URI2);

				Form form = new Form();
				form.param("jobID", app.getJobID());
				form.param("coverLetter", app.getCoverLetter());
				form.param("appID", app.getAppID());
				form.param("profileID", app.getProfileID());			
				client2.put(form);
				if(result.equals("yes")){
					form.param("status", "autoCheck-passed");
				}else{
					form.param("status", "autoCheck-rejected");
				}
				
				client2.path("/update").type(MediaType.APPLICATION_FORM_URLENCODED);
				client2.put(form);

			}

			boolean create = true;
			out.print(create);

			reader.close();
		} catch (Exception e) {
			// return succeed
			out.write("failed");
			out.close();
			return;
		}
		out.close();
	}
}
