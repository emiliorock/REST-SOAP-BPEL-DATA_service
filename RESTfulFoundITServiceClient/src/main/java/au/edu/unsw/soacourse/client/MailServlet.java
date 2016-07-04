package au.edu.unsw.soacourse.client;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;


import org.apache.cxf.jaxrs.client.WebClient;

import java.io.IOException;


/**
 * Servlet implementation class RESTfulFoundITServiceClient
 */
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String keyword = request.getParameter("keyword").toLowerCase();
		String sort_by = request.getParameter("sort_by").toLowerCase();
		String email = request.getParameter("email");

		System.out.println(keyword + sort_by + email);

		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");

		
		String REST_URI = "http://localhost:8080/RESTfulFoundITDataService/jobalerts";
		WebClient client = WebClient.create(REST_URI);

		Form form = new Form();
		form.param("keyword", keyword);
		form.param("sort_by", sort_by);
		form.param("email", email);
		client.path("/").type(MediaType.APPLICATION_FORM_URLENCODED);
		client.post(form);

		client.back(true);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
		
		return;

	}
}
