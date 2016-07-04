package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.DAO.userProfileDAO;
import au.edu.unsw.soacourse.foundIT.modeler.userProfileBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.userProfileService;

public class userProfileImpl implements userProfileService {

	@Override
	public String createProfile(userProfileBean userPro) {
		userProfileDAO userProfileDAO=new userProfileDAO();
		SecureRandom random = new SecureRandom();
		int newProfileID=random.nextInt(10000);
		userPro.setId(Integer.toString(newProfileID));
		userProfileDAO.createUser(userPro);
		return Integer.toString(newProfileID);
	}

	@Override
	public boolean deleteProfile(String id) {
		userProfileDAO userProfileDAO=new userProfileDAO();
		
		boolean flag=false;
		List<userProfileBean> tempList=userProfileDAO.getAllUsers();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id))
				flag=true;
		}
		if(flag){
		userProfileDAO.deleteUser(id);
		return true;
		}
		
		return false;
	}

	@Override
	public List<userProfileBean> getAllUserProfiles() {
		userProfileDAO userProfileDAO=new userProfileDAO();
		List<userProfileBean> tempList=userProfileDAO.getAllUsers();
		return tempList;
	}

	@Override
	public boolean updataProfile(userProfileBean userPro) {
		String id=userPro.getId();
		boolean flag=false;
		userProfileDAO userProfileDAO=new userProfileDAO();
		List<userProfileBean> tempList=userProfileDAO.getAllUsers();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id))
				flag=true;
		}
		if(flag){
		userProfileDAO.updateUser(userPro);
		return true;
		}
		
		return false;
	}

	@Override
	public userProfileBean getSpecificProfile(String id) {
		boolean flag=false;
		userProfileDAO userProfileDAO=new userProfileDAO();
		List<userProfileBean> tempList=userProfileDAO.getAllUsers();
		userProfileBean resultBean=new userProfileBean();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getId().equals(id)&&tempList.get(i).getId()!=null)
				flag=true;
		}
		if(flag){
		Element element=(Element) userProfileDAO.selectSingleNode(id);
		resultBean.setId(element.getElementsByTagName("id").item(0).getTextContent());
		resultBean.setDetail(element.getElementsByTagName("detail").item(0).getTextContent());
		resultBean.setExperience(element.getElementsByTagName("exp").item(0).getTextContent());
		resultBean.setSkill(element.getElementsByTagName("skill").item(0).getTextContent());
		return resultBean;
		}else {
			return null;
		}
		
	}

}
