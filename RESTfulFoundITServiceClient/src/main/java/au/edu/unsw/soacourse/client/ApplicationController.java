package au.edu.unsw.soacourse.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApplicationController
 */
public class ApplicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationController() {
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

	
	private void dispatchRequests(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forwardPage = "";
		String action = request.getParameter("action");

		try {
			if ((action == null)) {
				forwardPage = "404NotFound.jsp";
			} else if (action.equals("getAppsByJobID")) {
				ApplicationLogic.getAppsByJobID(request, response);
			} else if (action.equals("getAppsByAppID")) {
				ApplicationLogic.getAppsByAppID(request, response);
			}else if (action.equals("getApps")) {
				ApplicationLogic.getApps(request, response);
			} else if (action.equals("deleteApp")) {
				ApplicationLogic.deleteApps(request, response);
			} else if (action.equals("updateApp")) {
					ApplicationLogic.updateApp(request, response);
			} else if (action.equals("createApp")) {
				System.out.println("create");
				ApplicationLogic.createApp(request, response);
			} else if (action.equals("autocheck")) {
				ApplicationLogic.autocheck(request, response);
			} else {
				forwardPage = "404NotFound.jsp";
			}

		} catch (Exception e) {
		}
	}
}
