package au.edu.unsw.soacourse.foundIT.dao;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import au.edu.unsw.soacourse.foundIT.model.UserProfile;

public class UserProfileDAO {
	private Document doc;
	private String path;
	private String localpath = "C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\userProfiles.xml";
	File f;
	File localFile = new File(localpath);

	public UserProfileDAO() {
		init();
	}

	public void init() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			f = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "userProfiles.xml");
			path = f.toString();
			// System.out.println(path);
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
		} catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public UserProfile createUserProfile(UserProfile up) {
		Element docRoot = doc.getDocumentElement();
		UserProfile upBean = up;
		int ID = 0;

		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element userP = (Element) node;
				NodeList proIDN = userP.getElementsByTagName("profileID");
				int maxID = Integer.parseInt(proIDN.item(0).getTextContent());
				if (maxID > ID) {
					ID = maxID;
				}
			}
		}
		ID = ID + 1;
		Element newProfile = doc.createElement("profile");

		Element profileID = doc.createElement("profileID");
		profileID.setTextContent(String.valueOf(ID));
		newProfile.appendChild(profileID);

		Element userID = doc.createElement("userID");
		userID.setTextContent(up.getUserID());
		newProfile.appendChild(userID);

		Element firstname = doc.createElement("firstname");
		firstname.setTextContent(up.getFirstname());
		newProfile.appendChild(firstname);

		Element lastname = doc.createElement("lastname");
		lastname.setTextContent(up.getLastname());
		newProfile.appendChild(lastname);

		Element email = doc.createElement("email");
		email.setTextContent(up.getEmail());
		newProfile.appendChild(email);

		Element phone = doc.createElement("phone");
		phone.setTextContent(up.getPhone());
		newProfile.appendChild(phone);

		Element driverLicense = doc.createElement("driverLicense");
		driverLicense.setTextContent(up.getDriverLicense());
		newProfile.appendChild(driverLicense);

		Element address = doc.createElement("address");
		address.setTextContent(up.getAddress());
		newProfile.appendChild(address);

		Element professionalSkills = doc.createElement("professionalSkills");
		professionalSkills.setTextContent(up.getProfessionalSkills());
		newProfile.appendChild(professionalSkills);

		Element experience = doc.createElement("experience");
		experience.setTextContent(up.getExperience());
		newProfile.appendChild(experience);

		Element img = doc.createElement("img");
		img.setTextContent(up.getImg());
		newProfile.appendChild(img);

		docRoot.appendChild(newProfile);

		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);

			Files.copy(f.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (TransformerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		upBean.setProfileID(String.valueOf(ID));
		return up;
	}

	public UserProfile getProfileByUserID(String userID) {
		UserProfile up = new UserProfile();
		String profileID;
		if (userIDToProfileID(userID) == null) {
			return null;
		} else {
			profileID = userIDToProfileID(userID);
			up = getProfileByProfileID(profileID);
			return up;
		}
	}

	public UserProfile getProfileByProfileID(String profileID) {
		UserProfile up = new UserProfile();
		Element element = findByProfileId(profileID);

		if (element == null) {
			return null;
		} else {
			up.setProfileID(element.getElementsByTagName("profileID").item(0).getTextContent());
			up.setUserID(element.getElementsByTagName("userID").item(0).getTextContent());
			up.setFirstname(element.getElementsByTagName("firstname").item(0).getTextContent());
			up.setLastname(element.getElementsByTagName("lastname").item(0).getTextContent());
			up.setEmail(element.getElementsByTagName("email").item(0).getTextContent());
			up.setPhone(element.getElementsByTagName("phone").item(0).getTextContent());
			up.setDriverLicense(element.getElementsByTagName("driverLicense").item(0).getTextContent());
			up.setAddress(element.getElementsByTagName("address").item(0).getTextContent());
			up.setProfessionalSkills(element.getElementsByTagName("professionalSkills").item(0).getTextContent());
			up.setExperience(element.getElementsByTagName("experience").item(0).getTextContent());
			up.setImg(element.getElementsByTagName("img").item(0).getTextContent());

			return up;
		}
	}

	public UserProfile updateProfile(UserProfile up) {
		UserProfile upBean = up;
		Element element = findByProfileId(up.getProfileID());

		element.getElementsByTagName("firstname").item(0).setTextContent(up.getFirstname());
		element.getElementsByTagName("lastname").item(0).setTextContent(up.getLastname());
		element.getElementsByTagName("email").item(0).setTextContent(up.getEmail());
		element.getElementsByTagName("phone").item(0).setTextContent(up.getPhone());
		element.getElementsByTagName("driverLicense").item(0).setTextContent(up.getDriverLicense());
		element.getElementsByTagName("address").item(0).setTextContent(up.getAddress());
		element.getElementsByTagName("professionalSkills").item(0).setTextContent(up.getProfessionalSkills());
		element.getElementsByTagName("experience").item(0).setTextContent(up.getExperience());
		element.getElementsByTagName("img").item(0).setTextContent(up.getImg());
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			Files.copy(f.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (TransformerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return upBean;
	}

	public boolean deleteProfile(String profileID) {
		Element element = findByProfileId(profileID);
		if (element == null) {
			return false;
		} else {
			doc.getDocumentElement().removeChild(element);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			try {
				Transformer transformer = tFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(path));
				transformer.transform(source, result);
				Files.copy(f.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (TransformerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

	}

	public String userIDToProfileID(String userID) {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String expr = "//userProfiles/profile[userID='" + userID + "']";
		Element element = null;
		String profileID = null;
		try {
			element = (Element) xpath.evaluate(expr, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (element == null) {
			return null;
		} else {
			profileID = element.getElementsByTagName("profileID").item(0).getTextContent();
			return profileID;
		}

	}

	public Element findByProfileId(String profileID) {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String expr = "//userProfiles/profile[profileID='" + profileID + "']";
		Element element = null;
		try {
			element = (Element) xpath.evaluate(expr, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element;
	}

}
