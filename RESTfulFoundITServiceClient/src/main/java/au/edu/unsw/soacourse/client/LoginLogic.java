package au.edu.unsw.soacourse.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import au.edu.unsw.soacourse.beans.CompanyProfile;
import au.edu.unsw.soacourse.beans.UserProfile;
import au.edu.unsw.soacourse.user.User;
import au.edu.unsw.soacourse.user.UsersDAO;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;

public class LoginLogic {
	static public boolean normalLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
	try{
			// take in the json
			BufferedReader reader = request.getReader();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			// convert to object
			UsersDAO userDAO= new UsersDAO();
			User user = gson.fromJson(sb.toString(), User.class);
			String pwd = user.getPassword();
			User userInDB = userDAO.getUser(user);
			if(userInDB == null){
				out.write("login failed");
				out.close();
				return false;
			}
			if(pwd.equals(userInDB.getPassword())){
				out.write("login succeed");
				out.close();	

			}else{
				out.write("login failed");
				out.close();
				return false;
			}	
			// if passed all above, we can add this user to session
			HttpSession session = request.getSession();
			// now, the user bean is in the session, which we can get and use anywhere in this session
			session.setAttribute("userSession", user);
			if(user.getUserType().equals("user")){
				String REST_URI = "http://localhost:8080/RESTfulFoundITService/userProfiles";
				WebClient client = WebClient.create(REST_URI);
				String s = "";
				client.path("/user/"+user.getUserID()).accept(MediaType.APPLICATION_JSON);
				s = client.get(String.class);	
				UserProfile up = gson.fromJson(s, UserProfile.class);
				session.setAttribute("userProfileSession", up);
			}else{
				String REST_URI = "http://localhost:8080/RESTfulFoundITService/companyProfiles";
				WebClient client = WebClient.create(REST_URI);
				String s = "";
				client.path("/manager/"+user.getUserID()).accept(MediaType.APPLICATION_JSON);
				s = client.get(String.class);	
				CompanyProfile cp = gson.fromJson(s, CompanyProfile.class);
				session.setAttribute("companyProfileSession", cp);
			}
				
			
			return true;
		}catch (Exception e) {
			out.write("login failed");
			out.close();
			return false;
		}
	}
	
	// take off the session bean, user log out
	static public void logout(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("userSession");
		session.removeAttribute("userProfileSession");
		session.removeAttribute("companyProfileSession");
		session.removeAttribute("reviewerSession");
	}
}
