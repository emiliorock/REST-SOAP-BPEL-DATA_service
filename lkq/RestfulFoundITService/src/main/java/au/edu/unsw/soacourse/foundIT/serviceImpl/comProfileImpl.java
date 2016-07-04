package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.List;

import org.w3c.dom.Element;

import au.edu.unsw.soacourse.foundIT.DAO.comProfileDAO;
import au.edu.unsw.soacourse.foundIT.modeler.companyProfileBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.comProfileService;

public class comProfileImpl implements comProfileService {

	@Override
	public String createProfile(companyProfileBean comPro) {
		comProfileDAO comProfile = new comProfileDAO();
		SecureRandom random = new SecureRandom();
		int newProfileID = random.nextInt(10000);
		comPro.setProfileID(Integer.toString(newProfileID));
		comProfile.createCompany(comPro);
		return Integer.toString(newProfileID);
	}

	@Override
	public boolean deleteProfile(String id) {
		comProfileDAO userProfileDAO = new comProfileDAO();

		boolean flag = false;
		List<companyProfileBean> tempList = userProfileDAO.getAllCompany();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getProfileID().equals(id))
				flag = true;
		}
		if (flag) {
			userProfileDAO.deleteCompany(id);
			return true;
		}

		return false;

	}

	@Override
	public List<companyProfileBean> getAllComProfiles() {
		comProfileDAO userProfileDAO = new comProfileDAO();
		List<companyProfileBean> tempList = userProfileDAO.getAllCompany();
		return tempList;
	}

	@Override
	public boolean updataProfile(companyProfileBean comPro) {

		String id = comPro.getProfileID();
		boolean flag = false;
		comProfileDAO comProfileDAO = new comProfileDAO();
		List<companyProfileBean> tempList = comProfileDAO.getAllCompany();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getProfileID().equals(id))
				flag = true;
		}
		if (flag) {
			comProfileDAO.updateCompany(comPro);
			return true;
		}

		return false;
	}

	@Override
	public companyProfileBean getSpecificProfile(String id) {
		boolean flag = false;
		comProfileDAO comProfileDAO = new comProfileDAO();
		List<companyProfileBean> tempList = comProfileDAO.getAllCompany();
		companyProfileBean resultBean = new companyProfileBean();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getProfileID().equals(id)
					&& tempList.get(i).getProfileID() != null)
				flag = true;
		}
		if (flag) {
			Element element = (Element) comProfileDAO.selectSingleNode(id);
			resultBean.setProfileID(element.getElementsByTagName("id").item(0)
					.getTextContent());
			resultBean.setDetail(element.getElementsByTagName("detail").item(0)
					.getTextContent());
			resultBean.setAddress(element.getElementsByTagName("address")
					.item(0).getTextContent());
			resultBean.setPhone(element.getElementsByTagName("phone").item(0)
					.getTextContent());
			return resultBean;
		} else {
			return null;
		}

	}

}
