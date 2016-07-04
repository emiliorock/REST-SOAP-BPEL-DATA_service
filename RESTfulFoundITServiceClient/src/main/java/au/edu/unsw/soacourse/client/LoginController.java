package au.edu.unsw.soacourse.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginController
 */

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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

		try {
			if ((action == null)) {
				forwardPage = "404NotFound.jsp";
			} else if (action.equals("login")) {
				// if normal login achieved
				LoginLogic.normalLogin(request, response);
			} else if (action.equals("logout")) {
				// if normal login achieved
				LoginLogic.logout(request, response);
			} else {
				forwardPage = "404NotFound.jsp";
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + forwardPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {

		}

	}
}
