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

import au.edu.unsw.soacourse.beans.UserProfile;
import au.edu.unsw.soacourse.user.User;
import au.edu.unsw.soacourse.user.UsersDAO;

public class RegisterLogic {
	static public void checkeml(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UsersDAO usersDAO = new UsersDAO();
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
			User user = gson.fromJson(sb.toString(), User.class);
			// check

			boolean eml = usersDAO.ifEmailExist(user.getEmail());
			out.print(eml);
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

	static public void checknew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UsersDAO usersDAO = new UsersDAO();
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
			User user = gson.fromJson(sb.toString(), User.class);
			// check
			boolean add = true;
			boolean eml = usersDAO.ifEmailExist(user.getEmail());
			if (eml)
				add = false;
			if (user.getPassword() == null || user.getPassword().equals(""))
				add = false;
			if (user.getUserType() == null)
				add = false;

			if (add) {
				usersDAO.createUser(user);
			}

			out.print(add);
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

	static public void checkupd(HttpServletRequest request, HttpServletResponse response)
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
			UserProfile up = gson.fromJson(sb.toString(), UserProfile.class);
			// check
			boolean upd = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("userID", up.getUserID());
			form.param("firstname", up.getFirstname());
			form.param("lastname", up.getLastname());
			form.param("email", up.getEmail());
			form.param("phone", up.getPhone());
			form.param("driverLicense", up.getDriverLicense());
			form.param("address", up.getLastname());
			form.param("professionalSkills", up.getProfessionalSkills());
			form.param("experience", up.getExperience());
			form.param("img", up.getImg());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.put(form);

			client.back(true);
			client.path("/user/" + up.getUserID()).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			UserProfile newUp = gson.fromJson(s, UserProfile.class);
			HttpSession session = request.getSession();
			session.setAttribute("userProfileSession", newUp);

			out.print(upd);
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
			UserProfile up = gson.fromJson(sb.toString(), UserProfile.class);
			// check
			boolean upd = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";

			Form form = new Form();
			form.param("userID", up.getUserID());
			form.param("firstname", up.getFirstname());
			form.param("lastname", up.getLastname());
			form.param("email", up.getEmail());
			form.param("phone", up.getPhone());
			form.param("driverLicense", up.getDriverLicense());
			form.param("address", up.getLastname());
			form.param("professionalSkills", up.getProfessionalSkills());
			form.param("experience", up.getExperience());
			form.param("img", up.getImg());
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);

			client.back(true);
			client.path("/user/" + up.getUserID()).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			UserProfile newUp = gson.fromJson(s, UserProfile.class);
			HttpSession session = request.getSession();
			session.setAttribute("userProfileSession", newUp);

			out.print(upd);
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
			UserProfile up = gson.fromJson(sb.toString(), UserProfile.class);
			// check
			boolean delete = true;
			String userID = up.getUserID();
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
			WebClient client = WebClient.create(REST_URI);
			String s = "";
			client.path("/" + userID).delete();
			HttpSession session = request.getSession();
			session.removeAttribute("userProfileSession");

			client.back(true);
			client.path("/user/" + userID).accept(MediaType.APPLICATION_JSON);
			s = client.get(String.class);
			UserProfile newUp = gson.fromJson(s, UserProfile.class);

			session.setAttribute("userProfileSession", newUp);

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

			String profileID = "";
			if (jo.has("profileID"))
				profileID = jo.get("profileID").getAsString();

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
			REST_URI += "/profile/" + profileID;

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
}
