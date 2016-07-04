package au.edu.unsw.soacourse.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManagerController
 */
public class ManagerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerController() {
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
			} else if (action.equals("getJobs")) {
				// get the books
				ManagerLogic.getJobs(request, response);
			} else if (action.equals("addJob")) {
				// get the books
				ManagerLogic.addJob(request, response);
			} else if (action.equals("deleteJob")) {
				// get the books
				ManagerLogic.deleteJob(request, response);
			} else if (action.equals("updateJob")) {
				// get the books
				ManagerLogic.updateJob(request, response);
			} else if (action.equals("getReviewers")) {
				ManagerLogic.getReviewers(request, response);
			} else if (action.equals("addReviewer")) {
				ManagerLogic.addReviewer(request, response);
			} else if (action.equals("deleteReviewer")) {
				ManagerLogic.deleteReviewer(request, response);
			} else if (action.equals("updateReviewer")) {
				ManagerLogic.updateReviewer(request, response);
			} else {
				forwardPage = "404NotFound.jsp";
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + forwardPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {
		}
	}
}
