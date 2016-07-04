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

import au.edu.unsw.soacourse.beans.Application;
import au.edu.unsw.soacourse.beans.Review;
import au.edu.unsw.soacourse.beans.Reviewer;

public class ReviewLogic {
	static public void getReview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Reviewer rw = (Reviewer) session.getAttribute("reviewerSession");
		String reviewerID = rw.getReviewerID();

		String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviews";
		REST_URI += "/reviewerID/" + reviewerID;

		WebClient client = WebClient.create(REST_URI);
		String s = client.get(String.class);

		// System.out.println(s);

		// write to json

		out.write(s);
		out.close();

	}

	static public void createRw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("create");
		
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
			
			System.out.println(sb.toString());
			// System.out.println(sb.toString());
			// convert to object
			Gson gson = new Gson();
			JsonElement je = gson.fromJson(sb.toString(), JsonElement.class);
			JsonObject jo = je.getAsJsonObject();

			String rw = "";
			if (jo.has("reviewer"))
				rw = jo.get("reviewer").getAsString();
			
			String appID = "";
			if (jo.has("appID"))
				appID = jo.get("appID").getAsString();

			// check

		//	System.out.println("rw1"+rw);
			
			boolean upd = true;

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviews/byname/";
			WebClient client = WebClient.create(REST_URI);
			
			Form form = new Form();
			form.param("username", rw);
			form.param("appID", appID);
			client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
			client.post(form);
			

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
}
