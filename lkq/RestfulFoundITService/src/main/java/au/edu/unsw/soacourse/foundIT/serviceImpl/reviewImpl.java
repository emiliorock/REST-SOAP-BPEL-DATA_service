package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.foundIT.DAO.reviewDAO;
import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.reviewService;

public class reviewImpl implements reviewService {

	@Override
	public String createReview(reviewBean rev) {
		reviewDAO revDAO=new reviewDAO();
		SecureRandom random = new SecureRandom();
		int newRevID=random.nextInt(10000);
		rev.setReviewId(Integer.toString(newRevID));
		revDAO.createReview(rev);
		return Integer.toString(newRevID);
	}

	@Override
	public boolean deleteReview(String id) {
		reviewDAO revDAO=new reviewDAO();
		
		boolean flag=false;
		List<reviewBean> tempList = revDAO.getAllReviews();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getReviewId().equals(id))
				flag=true;
		}
		if(flag){
		revDAO.deleteReview(id);
		return true;
		}
		
		return false;
	}

	@Override
	public List<reviewBean> getAllReviews() {
		reviewDAO revDAO=new reviewDAO();
		List<reviewBean> tempList=revDAO.getAllReviews();
		return tempList;
	}

	public boolean updateReview(reviewBean rev) {
		String id=rev.getReviewId();
		boolean flag=false;
		reviewDAO revDAO=new reviewDAO();
		List<reviewBean> tempList=revDAO.getAllReviews();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getReviewId().equals(id))
				flag=true;
		}
		if(flag){
		revDAO.updateReview(rev);
		return true;
		}
		
		return false;
	}

	@Override
	public reviewBean getSpecificReview(String id) {
		boolean flag=false;
		reviewDAO revDAO=new reviewDAO();
		List<reviewBean> tempList=revDAO.getAllReviews();
		reviewBean resultBean=new reviewBean();
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).getReviewId().equals(id)&&tempList.get(i).getReviewId()!=null)
				flag=true;
		}
		if(flag){
		Element element=(Element) revDAO.selectSingleNode(id);
		resultBean.setReviewId(element.getElementsByTagName("reviewId").item(0).getTextContent());
		resultBean.setAppId(element.getElementsByTagName("appId").item(0).getTextContent());
		resultBean.setuId(element.getElementsByTagName("uId").item(0).getTextContent());
		resultBean.setComments(element.getElementsByTagName("comments").item(0).getTextContent());
		resultBean.setDecision(element.getElementsByTagName("decision").item(0).getTextContent());
		return resultBean;
		}else {
			return null;
		}
		
	}
	
	public List<reviewBean> getAppReviews(String appId) {
		boolean flag=false;
		reviewDAO revDAO=new reviewDAO();
		List<reviewBean> tempList=revDAO.getAllReviews();
		//System.out.println(tempList.get(0).getuId());
		List<reviewBean> resultList = new ArrayList<reviewBean>();
		
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			reviewBean resultBean = new reviewBean();
			if(tempList.get(i).getAppId().equals(appId)&&tempList.get(i).getAppId()!=null){
				//flag=true;
			System.out.println(tempList.get(i).getReviewId());
			Element element=(Element) revDAO.selectSingleNode(tempList.get(i).getReviewId());
			resultBean.setReviewId(element.getElementsByTagName("reviewId").item(0).getTextContent());
			resultBean.setAppId(element.getElementsByTagName("appId").item(0).getTextContent());
			resultBean.setuId(element.getElementsByTagName("uId").item(0).getTextContent());
			resultBean.setComments(element.getElementsByTagName("comments").item(0).getTextContent());
			resultBean.setDecision(element.getElementsByTagName("decision").item(0).getTextContent());
			resultList.add(resultBean);
			}
			
		}
		System.out.println(resultList.get(0).getReviewId()+resultList.get(1).getReviewId()+resultList.get(2).getReviewId());

		return resultList;
		
	}
	
	public List<reviewBean> getReviewerReviews(String uId) {
		boolean flag=false;
		reviewDAO revDAO=new reviewDAO();
		List<reviewBean> tempList=revDAO.getAllReviews();
		//System.out.println(tempList.get(0).getuId());
		List<reviewBean> resultList = new ArrayList<reviewBean>();
		
		
		//check the id whether exists
		for(int i=0;i<tempList.size();i++)
		{
			reviewBean resultBean = new reviewBean();
			if(tempList.get(i).getuId().equals(uId)&&tempList.get(i).getuId()!=null){
				//flag=true;
			System.out.println(tempList.get(i).getReviewId());
			Element element=(Element) revDAO.selectSingleNode(tempList.get(i).getReviewId());
			resultBean.setReviewId(element.getElementsByTagName("reviewId").item(0).getTextContent());
			resultBean.setAppId(element.getElementsByTagName("appId").item(0).getTextContent());
			resultBean.setuId(element.getElementsByTagName("uId").item(0).getTextContent());
			resultBean.setComments(element.getElementsByTagName("comments").item(0).getTextContent());
			resultBean.setDecision(element.getElementsByTagName("decision").item(0).getTextContent());
			resultList.add(resultBean);
			}
			
		}
		

		return resultList;
		
	}


}
