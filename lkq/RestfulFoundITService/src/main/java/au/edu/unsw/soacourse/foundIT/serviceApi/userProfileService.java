package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.userProfileBean;

public interface userProfileService {
	
	String createProfile(userProfileBean userPro);
	
	boolean deleteProfile(String id);
	
	List<userProfileBean> getAllUserProfiles();
	
	boolean updataProfile(userProfileBean userPro);
	
	userProfileBean getSpecificProfile(String id);

}
