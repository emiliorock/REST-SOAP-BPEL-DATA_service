package au.edu.unsw.soacourse.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import au.edu.unsw.soacourse.beans.Reviewer;
import org.apache.cxf.jaxrs.client.WebClient;

public class ReviewerLogic {
	static public boolean normalLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			
			
			String REST_URI = "http://localhost:8080/RESTfulFoundITService/reviewers/validate/";
			REST_URI += "?username="+rw.getUsername()+"&password="+rw.getPassword();
		//	System.out.println(REST_URI);
			boolean exist = false;
			WebClient client = WebClient.create(REST_URI);
			if(client.get().getStatus()!=404){
				String s = client.get(String.class);
			//	System.out.println(s);
				Reviewer currentRW = gson.fromJson(s, Reviewer.class);
				HttpSession session = request.getSession();
				session.setAttribute("reviewerSession", currentRW);		
				exist = true;
			}		
			
			if(exist){
				out.write("login succeed");
				out.close();
				return true;
			}else{
				out.write("login failed");
				out.close();
				return false;
			}

		} catch (Exception e) {
			out.write("login failed");
			out.close();
			return false;
		}
	}

	// take off the session bean, user log out
	static public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("reviewerSession");
		session.removeAttribute("userSession");
		session.removeAttribute("userProfileSession");
		session.removeAttribute("companyProfileSession");
	}
}
