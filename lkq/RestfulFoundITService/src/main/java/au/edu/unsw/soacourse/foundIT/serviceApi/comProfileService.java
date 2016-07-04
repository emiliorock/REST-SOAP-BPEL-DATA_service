package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import au.edu.unsw.soacourse.foundIT.modeler.companyProfileBean;

public interface comProfileService  {

	String createProfile(companyProfileBean comPro);
	
	boolean deleteProfile(String id);
	
	List<companyProfileBean> getAllComProfiles();
	
	boolean updataProfile(companyProfileBean comPro);
	
	companyProfileBean getSpecificProfile(String id);
}
