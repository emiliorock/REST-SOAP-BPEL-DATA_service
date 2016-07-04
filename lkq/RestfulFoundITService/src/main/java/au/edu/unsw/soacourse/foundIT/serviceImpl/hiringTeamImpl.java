package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.foundIT.DAO.hiringTeamDAO;
import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.hiringTeamService;

public class hiringTeamImpl implements hiringTeamService {

	@Override
	public String createTeam(hiringTeamBean team) {
		hiringTeamDAO teamDAO=new hiringTeamDAO();
		SecureRandom random = new SecureRandom();
		int newTeamID=random.nextInt(10000);
		team.settId(Integer.toString(newTeamID));
		teamDAO.createTeam(team);
		return Integer.toString(newTeamID);
	}

	
	public List<hiringTeamBean> getAllHiringTeams() {
		hiringTeamDAO teamDAO=new hiringTeamDAO();
		List<hiringTeamBean> tempList=teamDAO.getAllHiringTeams();
		return tempList;
	}
	

}
