package au.edu.unsw.soacourse.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CompanyController
 */
public class CompanyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchRequests(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchRequests(request, response);
	}
	
	
	private void dispatchRequests(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forwardPage = "";
		String action = request.getParameter("action");

		try {
			if ((action == null)) {
				forwardPage = "404NotFound.jsp";
			} else if (action.equals("createpro")) {
				// if normal login achieved
				CompanyLogic.createpro(request, response);
			} else if (action.equals("deletepro")) {
				// if normal login achieved
				CompanyLogic.deletepro(request, response);
			} else if (action.equals("getpro")) {
				// if normal login achieved
				CompanyLogic.getpro(request, response);
			} else if (action.equals("updatepro")) {
				// if normal login achieved
				CompanyLogic.updatepro(request, response);
			}else {
				forwardPage = "404NotFound.jsp";
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + forwardPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {

		}

	}

}
