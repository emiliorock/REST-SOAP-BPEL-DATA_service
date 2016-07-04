package au.edu.unsw.soacourse.foundIT.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyProfile {
	private String companyID;
	private String managerID;
	private String name;
	private String url;
	private String intro;
	private String img;
	
	public CompanyProfile(){
		
	}

	public CompanyProfile(String companyID, String managerID, String name, String url, String intro, String img) {
		this.companyID = companyID;
		this.managerID = managerID;
		this.name = name;
		this.url = url;
		this.intro = intro;
		this.img = img;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}	
}
