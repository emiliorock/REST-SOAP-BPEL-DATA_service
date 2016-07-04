package au.edu.unsw.soacourse.foundIT.email;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;

@Path("/jobalerts")
public class MailResource {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// POST to send an email
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response newEmail(
			// @FormParam("id") String id,
			@FormParam("keyword") String keyword, @FormParam("sort_by") String sort_by,
			@FormParam("email") String email) throws IOException {
		System.out.println(keyword + sort_by + email);
		MailDAO dao = new MailDAO();
		try {
			dao.sendRSS(keyword, sort_by, email);
		} catch (TransformerException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).build();
	}
}