package au.edu.unsw.soacourse.beans;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfile {
	private String profileID;
	private String userID;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String driverLicense;
	private String address;
	private String professionalSkills;
	private String experience;
	private String img;
	
	public UserProfile(){
		
	}

	public UserProfile(String profileID, String userID, String firstname, String lastname, String email, String phone,
			String driverLicense, String address, String professionalSkills, String experience, String img) {
		this.profileID = profileID;
		this.userID = userID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.driverLicense = driverLicense;
		this.address = address;
		this.professionalSkills = professionalSkills;
		this.experience = experience;
		this.img = img;
	}

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfessionalSkills() {
		return professionalSkills;
	}

	public void setProfessionalSkills(String professionalSkills) {
		this.professionalSkills = professionalSkills;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
