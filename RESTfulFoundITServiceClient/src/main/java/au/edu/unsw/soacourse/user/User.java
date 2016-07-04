package au.edu.unsw.soacourse.user;

public class User {
	private String userID;
	private String email;
	private String password;
	private String userType;
	
	public User(){
		
	}
	
	public User(String userID, String email, String password, String userType){
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
