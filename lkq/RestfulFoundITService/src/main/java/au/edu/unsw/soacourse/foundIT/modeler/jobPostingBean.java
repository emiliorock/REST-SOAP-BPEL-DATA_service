package au.edu.unsw.soacourse.foundIT.modeler;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "comLink", "salaryRate", "positionType", "location", "description", "status" })
@XmlRootElement(name = "posting")
public class jobPostingBean {

	String id;
	String comLink;
	String salaryRate;
	String positionType;
	String location;
	String description;
	String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComLink() {
		return comLink;
	}

	public void setComLink(String comLink) {
		this.comLink = comLink;
	}

	public String getSalaryRate() {
		return salaryRate;
	}

	public void setSalaryRate(String salaryRate) {
		this.salaryRate = salaryRate;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
