package au.edu.unsw.soacourse.foundIT.dao;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.edu.unsw.soacourse.foundIT.model.Reviewer;



public class ReviewerDAO {
	private Document doc;
	private String path;
	private String localpath ="C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\reviewers.xml";
	File file;
	File localFile= new File(localpath);
	public ReviewerDAO() {
		init();
	}
	
	public void init(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			file = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "reviewers.xml");
			// System.out.println(f.toString());
			path = file.toString();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		} catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public Reviewer createReviewer(Reviewer rw) {
		Element docRoot = doc.getDocumentElement();
		int ID = 0;
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList aid = u.getElementsByTagName("reviewerID");
				int maxID = Integer.parseInt(aid.item(0).getTextContent());
				if (maxID > ID){
					ID = maxID;
				}
			}
		}
		ID = ID + 1;
		rw.setReviewerID(String.valueOf(ID));
		Reviewer return_value = new Reviewer();
		return_value = rw;
		
		Element newrw = doc.createElement("reviewer");
		
		Element reviewerID = doc.createElement("reviewerID");
		reviewerID.setTextContent(rw.getReviewerID());
		newrw.appendChild(reviewerID);
		
		Element companyID = doc.createElement("companyID");
		companyID.setTextContent(rw.getCompanyID());
		newrw.appendChild(companyID);
		
		Element username = doc.createElement("username");
		username.setTextContent(rw.getUsername());
		newrw.appendChild(username);
		
		Element password = doc.createElement("password");
		password.setTextContent(rw.getPassword());
		newrw.appendChild(password);
		
		Element professionalSkills = doc.createElement("professionalSkills");
		professionalSkills.setTextContent(rw.getProfessionalSkills());
		newrw.appendChild(professionalSkills);
		
		Element status = doc.createElement("status");
		status.setTextContent(rw.getStatus());
		newrw.appendChild(status);
		
		docRoot.appendChild(newrw);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//System.out.println("!");
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			Files.copy(file.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (TransformerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return return_value;
	}
	
	public boolean deleteReview(String id) {
		Element a = (Element)findByID(id);
		if (a == null) return false;
		doc.getDocumentElement().removeChild(a);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(doc), new StreamResult(new File(path)));
			Files.copy(file.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Reviewer updateReviewer(Reviewer rw) throws TransformerException {
		Reviewer return_value = new Reviewer();
		return_value = rw;
		String id = rw.getReviewerID();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		e.getElementsByTagName("reviewerID").item(0).setTextContent(rw.getReviewerID());
		e.getElementsByTagName("companyID").item(0).setTextContent(rw.getCompanyID());
		e.getElementsByTagName("username").item(0).setTextContent(rw.getUsername());
		e.getElementsByTagName("password").item(0).setTextContent(rw.getPassword());
		e.getElementsByTagName("professionalSkills").item(0).setTextContent(rw.getProfessionalSkills());
		e.getElementsByTagName("status").item(0).setTextContent(rw.getStatus());
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer f = factory.newTransformer();
		f.transform(new DOMSource(doc), new StreamResult(new File(path)));
		try {
			Files.copy(file.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return return_value;
	}
	
	public Reviewer getOneReviewer(String id) {
		Reviewer return_value = new Reviewer();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		return_value.setReviewerID(e.getElementsByTagName("reviewerID").item(0).getTextContent());
		return_value.setCompanyID(e.getElementsByTagName("companyID").item(0).getTextContent());
		return_value.setUsername(e.getElementsByTagName("username").item(0).getTextContent());
		return_value.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
		return_value.setProfessionalSkills(e.getElementsByTagName("professionalSkills").item(0).getTextContent());
		return_value.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
		return return_value;
	}
	
	public Reviewer getOneReviewerByName(String username) {
		Reviewer return_value = new Reviewer();
		Element e = (Element)findByName(username);
		if (e == null) return null;
		return_value.setReviewerID(e.getElementsByTagName("reviewerID").item(0).getTextContent());
		return_value.setCompanyID(e.getElementsByTagName("companyID").item(0).getTextContent());
		return_value.setUsername(e.getElementsByTagName("username").item(0).getTextContent());
		return_value.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
		return_value.setProfessionalSkills(e.getElementsByTagName("professionalSkills").item(0).getTextContent());
		return_value.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
		return return_value;
	}
	
	public ArrayList<Reviewer> getAllReviewers() {
		ArrayList<Reviewer> rwlist = new ArrayList<Reviewer>();
		Element docRoot = doc.getDocumentElement();
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			Reviewer rw = new Reviewer();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				rw.setReviewerID(e.getElementsByTagName("reviewerID").item(0).getTextContent());
				rw.setCompanyID(e.getElementsByTagName("companyID").item(0).getTextContent());
				rw.setUsername(e.getElementsByTagName("username").item(0).getTextContent());
				rw.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
				rw.setProfessionalSkills(e.getElementsByTagName("professionalSkills").item(0).getTextContent());
				rw.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
				rwlist.add(rw);
			}
		}
		return rwlist;
	}
	
	public Node findByID(String id) {
		Node result = null;
		Element root = doc.getDocumentElement();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String str = "//reviewers/reviewer[reviewerID='" + id + "']";
		try {
			result = (Node) xpath.evaluate(str, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Node findByName(String username) {
		Node result = null;
		Element root = doc.getDocumentElement();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String str = "//reviewers/reviewer[username='" + username + "']";
		try {
			result = (Node) xpath.evaluate(str, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
