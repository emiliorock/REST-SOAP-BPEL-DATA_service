package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;

public interface applicationService {
	
	String createApplication(applicationBean app);
	
	boolean deleteApplication(String id);
	
	List<applicationBean> getAllApplications();
	
	boolean updateApplication(applicationBean app);
	
	applicationBean getSpecificApplication(String id);

}