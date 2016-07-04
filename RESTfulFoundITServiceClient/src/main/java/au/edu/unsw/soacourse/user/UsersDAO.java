package au.edu.unsw.soacourse.user;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UsersDAO {
	private Document doc;
	private String path;
	private String localpath ="C:\\codes\\web\\RESTfulFoundITServiceClient\\src\\main\\webapp\\WEB-INF\\users.xml";
	File f;
	File localFile= new File(localpath);
	public UsersDAO(){
		init();
	}
	
	public void init(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			f = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "users.xml");
	//		System.out.println(f.toString());
			path = f.toString();
			DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
		}catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e){
			e.printStackTrace();
		}
		
	}
	
	public boolean createUser(User user){
		Element docRoot = doc.getDocumentElement();
		int ID = 0;

		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element u = (Element) node;
				NodeList emailN = u.getElementsByTagName("email");
				if(emailN.item(0).getTextContent().equals(user.getEmail())){
					return false;
				}
				NodeList userIDN = u.getElementsByTagName("userID");
				int maxID = Integer.parseInt(userIDN.item(0).getTextContent());
				if (maxID > ID){
					ID = maxID;
				}
			}
		}
		
		ID = ID + 1;
		
		Element newUser = doc.createElement("user");
		
		Element userID = doc.createElement("userID");
		userID.setTextContent(String.valueOf(ID));
		newUser.appendChild(userID);
		
		Element email = doc.createElement("email");
		email.setTextContent(user.getEmail());
		newUser.appendChild(email);
		
		Element password = doc.createElement("password");
		password.setTextContent(user.getPassword());
		newUser.appendChild(password);
		
		Element userType = doc.createElement("userType");
		userType.setTextContent(user.getUserType());
		newUser.appendChild(userType);
		
		docRoot.appendChild(newUser);
		
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

	
	public User getUser(User user){
		Element docRoot = doc.getDocumentElement();
		User getUser = user;
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList emailN = u.getElementsByTagName("email");
				String email = emailN.item(0).getTextContent();
				if(email.equals(user.getEmail())){
					getUser.setEmail(email);
					getUser.setPassword(u.getElementsByTagName("password").item(0).getTextContent());
					getUser.setUserID(u.getElementsByTagName("userID").item(0).getTextContent());
					getUser.setUserType(u.getElementsByTagName("userType").item(0).getTextContent());			
					return getUser;
				}
			}
		}
				
		return null;
	}
	
	public boolean ifEmailExist(String e){
		Element docRoot = doc.getDocumentElement();
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList emailN = u.getElementsByTagName("email");
				String email = emailN.item(0).getTextContent();
				if(email.toLowerCase().equals(e.toLowerCase())){
					return true;
				}
			}
		}				
		return false;
	}
}
