package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.DAO.applicationDAO;
import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.applicationService;

public class applicationImpl implements applicationService {

	@Override
	public String createApplication(applicationBean app) {
		applicationDAO appDAO=new applicationDAO();
		SecureRandom random = new SecureRandom();
		int newAppID=random.nextInt(10000);
		app.setId(Integer.toString(newAppID));
		appDAO.createApplication(app);
		return Integer.toString(newAppID);
	}

	@Override
	public boolean deleteApplication(String id) {
		applicationDAO appDAO=new applicationDAO();
		
		boolean flag=false;
		List<applicationBean> tempList = appDAO.getAllApplications();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id))
				flag=true;
		}
		if(flag){
		appDAO.deleteApplication(id);
		return true;
		}
		
		return false;
	}

	@Override
	public List<applicationBean> getAllApplications() {
		applicationDAO appDAO=new applicationDAO();
		List<applicationBean> tempList=appDAO.getAllApplications();
		return tempList;
	}

	public boolean updateApplication(applicationBean app) {
		String id=app.getId();
		boolean flag=false;
		applicationDAO appDAO=new applicationDAO();
		List<applicationBean> tempList=appDAO.getAllApplications();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id))
				flag=true;
		}
		if(flag){
		appDAO.updateApplication(app);
		return true;
		}
		
		return false;
	}

	@Override
	public applicationBean getSpecificApplication(String id) {
		boolean flag=false;
		applicationDAO appDAO=new applicationDAO();
		List<applicationBean> tempList=appDAO.getAllApplications();
		applicationBean resultBean=new applicationBean();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id)&&tempList.get(i).getId()!=null)
				flag=true;
		}
		if(flag){
		Element element=(Element) appDAO.selectSingleNode(id);
		resultBean.setId(element.getElementsByTagName("id").item(0).getTextContent());
		resultBean.setLink(element.getElementsByTagName("link").item(0).getTextContent());
		resultBean.setCoverLetter(element.getElementsByTagName("coverLetter").item(0).getTextContent());
		resultBean.setStatus(element.getElementsByTagName("status").item(0).getTextContent());
		return resultBean;
		}else {
			return null;
		}
		
	}



}
