package au.edu.unsw.soacourse.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RegisterController
 */
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dispatchRequests(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dispatchRequests(request, response);
	}

	private void dispatchRequests(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forwardPage = "";
		String action = request.getParameter("action");
		
		// System.out.println(action);
		try {
			if ((action == null)) {
				throw new Exception();
			}

			if (action.equals("checkeml")) {
				RegisterLogic.checkeml(request, response);
				return;
			} else if (action.equals("checknew")) {
				RegisterLogic.checknew(request, response);
				return;
			}else if (action.equals("checkupd")) {
				RegisterLogic.checkupd(request, response);
				return;
			}else if (action.equals("createpro")) {
				RegisterLogic.createpro(request, response);
				return;
			}else if (action.equals("deletepro")) {
				RegisterLogic.deletepro(request, response);
				return;
			}else if (action.equals("getpro")) {
				RegisterLogic.getpro(request, response);
				return;
			}else
				forwardPage = "404NotFound.jsp";
		} catch (Exception e) {
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + forwardPage);
		dispatcher.forward(request, response);

	}
}
