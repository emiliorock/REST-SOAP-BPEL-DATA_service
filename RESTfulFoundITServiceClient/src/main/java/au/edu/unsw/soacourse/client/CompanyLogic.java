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

public class CompanyLogic {
	
	
	static public void createpro(HttpServletRequest request, HttpServletResponse response)
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
			CompanyProfile cp = gson.fromJson(sb.toString(), CompanyProfile.class);
			// check
			

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/companyProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("managerID", cp.getManagerID());
			form.param("name", cp.getName());
			form.param("intro", cp.getIntro());
			form.param("url", cp.getUrl());
			form.param("img", cp.getImg());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);

			client.back(true);
			client.path("/manager/" + cp.getManagerID()).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			CompanyProfile newCP = gson.fromJson(s, CompanyProfile.class);
			HttpSession session = request.getSession();
			session.setAttribute("companyProfileSession", newCP);
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
	
	static public void deletepro(HttpServletRequest request, HttpServletResponse response)
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
			CompanyProfile up = gson.fromJson(sb.toString(), CompanyProfile.class);
			// check
			boolean delete = true;
			String managerID = up.getManagerID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/companyProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";
			client.path("/"+ managerID).delete();
			HttpSession session = request.getSession();
			session.removeAttribute("companyProfileSession");

			client.back(true);
			client.path("/manager/" + managerID).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			CompanyProfile newCP = gson.fromJson(s, CompanyProfile.class);
			
			session.setAttribute("companyProfileSession", newCP);

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
	static public void getpro(HttpServletRequest request, HttpServletResponse response)
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

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/companyProfiles";
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
	static public void updatepro(HttpServletRequest request, HttpServletResponse response)
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
			CompanyProfile cp = gson.fromJson(sb.toString(), CompanyProfile.class);
			// check
			boolean upd = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/companyProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("managerID", cp.getManagerID());
			form.param("name", cp.getName());
			form.param("url", cp.getUrl());
			form.param("img", cp.getImg());
			form.param("intro", cp.getIntro());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.put(form);

			client.back(true);
			client.path("/company/" + cp.getCompanyID()).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			CompanyProfile newcp = gson.fromJson(s, CompanyProfile.class);
			HttpSession session = request.getSession();
			session.setAttribute("companyProfileSession", newcp);

			out.print(upd);
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
	
}
