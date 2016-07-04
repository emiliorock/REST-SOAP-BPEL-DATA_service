package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;


import au.edu.unsw.soacourse.foundIT.DAO.jobPostingDAO;
import au.edu.unsw.soacourse.foundIT.modeler.jobPostingBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.jobPostingService;

public class jobPostingImpl implements jobPostingService {

	@Override
	public String createPosting(jobPostingBean jobPost) {
		jobPostingDAO jobPosting = new jobPostingDAO();
		SecureRandom random = new SecureRandom();
		int newPostingID = random.nextInt(10000);
		jobPost.setId(Integer.toString(newPostingID));
		jobPosting.createPosting(jobPost);
		return Integer.toString(newPostingID);
	}
	public List<jobPostingBean> searchJobByKeyWord(String salaryRate,
			String positionType, String location, String status) {
		List<jobPostingBean> resuListBean=new ArrayList<jobPostingBean>();
		jobPostingDAO jobPosting = new jobPostingDAO();
		List<jobPostingBean> allJobList=jobPosting.getAllPostings();
		//System.out.println("111111111");
		//System.out.println(salaryRate.length()+"xixixixix");
		for(int i=0;i<allJobList.size();i++)
		{
			System.out.println("111111111");
			jobPostingBean JB=allJobList.get(i);
			
			String param_position=JB.getPositionType();
			//System.out.println("hahahaha"+param_position);
			String param_status=JB.getStatus();
			String param_salary=JB.getSalaryRate();
			String param_location=JB.getLocation();
			if(!salaryRate.isEmpty()&&salaryRate!=null)
			{
				System.out.println("2222");
				if(!salaryRate.equals(param_salary))
					continue;
			}
			if(!positionType.isEmpty()&&positionType!=null)
			{
				if(!positionType.equals(param_position))
					continue;
			}
			if(!status.isEmpty()&&status!=null)
			{
				if(!status.equals(param_status))
					continue;
			}
			if(!location.isEmpty()&&location!=null)
			{
				System.out.println("come location");
				if(!location.equals(param_location))
					continue;
			}
			resuListBean.add(JB);	
		}
		return resuListBean;

	}

	@Override
	public boolean deletePosting(String id) {
		jobPostingDAO jobPostingDAO = new jobPostingDAO();

		boolean flag = false;
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id))
				flag = true;
		}
		if (flag) {
			jobPostingDAO.deletePosting(id);
			return true;
		}

		return false;

	}

	@Override
	public List<jobPostingBean> getAllPostings() {
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();
		return tempList;
	}

	public boolean updatePosting(jobPostingBean jobPost) {

		String id = jobPost.getId();
		boolean flag = false;
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id))
				flag = true;
		}
		if (flag) {
			jobPostingDAO.updatePosting(jobPost);
			return true;
		}

		return false;
	}

	@Override
	public jobPostingBean getSpecificPosting(String id) {
		boolean flag = false;
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();
		jobPostingBean resultBean = new jobPostingBean();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id) && tempList.get(i).getId() != null)
				flag = true;
		}
		if (flag) {
			Element element = (Element) jobPostingDAO.selectSingleNode(id);
			resultBean.setId(element.getElementsByTagName("id").item(0).getTextContent());
			resultBean.setComLink(element.getElementsByTagName("comLink").item(0).getTextContent());
			resultBean.setSalaryRate(element.getElementsByTagName("salaryRate").item(0).getTextContent());
			resultBean.setPositionType(element.getElementsByTagName("positionType").item(0).getTextContent());
			resultBean.setLocation(element.getElementsByTagName("location").item(0).getTextContent());
			resultBean.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
			resultBean.setStatus(element.getElementsByTagName("status").item(0).getTextContent());
			return resultBean;
		} else {
			return null;
		}

	}

}
