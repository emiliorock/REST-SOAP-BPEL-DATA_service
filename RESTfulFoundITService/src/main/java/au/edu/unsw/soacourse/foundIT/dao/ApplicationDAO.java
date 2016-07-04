package au.edu.unsw.soacourse.foundIT.dao;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import au.edu.unsw.soacourse.foundIT.model.Application;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class ApplicationDAO {
	private Document doc;
	private String path;
	private String localpath ="C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\applications.xml";
	File file;
	File localFile= new File(localpath);
	public ApplicationDAO() {
		init();
	}

	public void init(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			file = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "applications.xml");
			path = file.toString();
			//System.out.println(file.toString());
			DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
		}catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e){
			e.printStackTrace();
		}		
	}
	
	public Node findByID(String id) {
		Node result = null;
		Element root = doc.getDocumentElement();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String str = "//applications/application[appID='" + id + "']";
		try {
			result = (Node) xpath.evaluate(str, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Application createApplication(Application app) {
		Element docRoot = doc.getDocumentElement();
		int ID = 0;
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList aid = u.getElementsByTagName("appID");
				int maxID = Integer.parseInt(aid.item(0).getTextContent());
				if (maxID > ID){
					ID = maxID;
				}
			}
		}
		ID = ID + 1;
		app.setAppID(String.valueOf(ID));
		Application return_value = new Application();
		return_value = app;
		
		Element newapp = doc.createElement("application");
		
		Element appID = doc.createElement("appID");
		appID.setTextContent(app.getAppID());
		newapp.appendChild(appID);
		
		Element jobID = doc.createElement("jobID");
		jobID.setTextContent(app.getJobID());
		newapp.appendChild(jobID);
		
		Element profileID = doc.createElement("profileID");
		profileID.setTextContent(app.getProfileID());
		newapp.appendChild(profileID);
		
		Element coverLetter = doc.createElement("coverLetter");
		coverLetter.setTextContent(app.getCoverLetter());
		newapp.appendChild(coverLetter);
		
		Element status = doc.createElement("status");
		status.setTextContent(app.getStatus());
		newapp.appendChild(status);
		
		docRoot.appendChild(newapp);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			System.out.println("!");
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			Files.copy(file.toPath(), localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (TransformerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return return_value;
	}
	
	public boolean deleteApplication(String id) {
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
	
	public Application updateApplication(Application app) throws TransformerException {
		Application return_value = new Application();
		return_value = app;
		String id = app.getAppID();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		e.getElementsByTagName("coverLetter").item(0).setTextContent(app.getCoverLetter());
		e.getElementsByTagName("status").item(0).setTextContent(app.getStatus());
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
	
	public Application getOneApplication(String id) {
		Application return_value = new Application();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		return_value.setAppID(e.getElementsByTagName("appID").item(0).getTextContent());
		return_value.setJobID(e.getElementsByTagName("jobID").item(0).getTextContent());
		return_value.setProfileID(e.getElementsByTagName("profileID").item(0).getTextContent());
		return_value.setCoverLetter(e.getElementsByTagName("coverLetter").item(0).getTextContent());
		return_value.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
		return return_value;
	}
	
	public ArrayList<Application> getAllApplications() {
		ArrayList<Application> applist = new ArrayList<Application>();
		Element docRoot = doc.getDocumentElement();
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			Application app = new Application();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				app.setAppID(e.getElementsByTagName("appID").item(0).getTextContent());
				app.setJobID(e.getElementsByTagName("jobID").item(0).getTextContent());
				app.setProfileID(e.getElementsByTagName("profileID").item(0).getTextContent());
				app.setCoverLetter(e.getElementsByTagName("coverLetter").item(0).getTextContent());
				app.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
				applist.add(app);
			}
		}
		return applist;
	}
	
}