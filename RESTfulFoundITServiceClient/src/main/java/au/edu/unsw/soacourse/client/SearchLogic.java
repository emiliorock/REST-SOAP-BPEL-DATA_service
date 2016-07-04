package au.edu.unsw.soacourse.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.jaxrs.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class SearchLogic {
	static public void search(HttpServletRequest request, HttpServletResponse response)
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

			String companyName ="";
			if(jo.has("companyName"))
				companyName= jo.get("companyName").getAsString();
			
			String salaryRate1 ="";	
			if(jo.has("salaryRate1"))
				salaryRate1= jo.get("salaryRate1").getAsString();
			
			String salaryRate2 ="";	
			if(jo.has("salaryRate2"))
				salaryRate2= jo.get("salaryRate2").getAsString();
			
			String positionType ="";	
			if(jo.has("positionType"))
				positionType= jo.get("positionType").getAsString();
			
			String location ="";	
			if(jo.has("location"))
				location= jo.get("location").getAsString();
			
			String description ="";	
			if(jo.has("description"))
				description= jo.get("description").getAsString();
	
			
			String status ="";	
			if(jo.has("status"))
				status= jo.get("status").getAsString();

			String REST_URI = "http://localhost:8080/RESTfulFoundITService/jobs";
			REST_URI +="/search?companyName=" + companyName + "&salaryRate1=" + salaryRate1 + "&salaryRate2="
					+ salaryRate2 + "&positionType=" + positionType + "&location=" + location + "&description="
					+ description + "&status=" + status;			
	
			WebClient client = WebClient.create(REST_URI);
			String s = client.get(String.class);
//			System.out.println(s);

//			// write to json
//			ArrayList<SellerBookDTO> books = cast.advSearch(jo, page);
//			String g = gson.toJson(books);

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
