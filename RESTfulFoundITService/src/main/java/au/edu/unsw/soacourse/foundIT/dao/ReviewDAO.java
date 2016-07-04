package au.edu.unsw.soacourse.foundIT.dao;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import au.edu.unsw.soacourse.foundIT.model.Review;
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

public class ReviewDAO {
	private Document doc;
	private String path;
	private String localpath ="C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\reviews.xml";
	File file;
	File localFile= new File(localpath);
	public ReviewDAO() {
		init();
	}

	public void init(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			file = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "reviews.xml");
			//System.out.println(f.toString());
			path = file.toString();
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
		String str = "//reviews/review[reviewID='" + id + "']";
		try {
			result = (Node) xpath.evaluate(str, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Review createReview(Review rw) {
		Element docRoot = doc.getDocumentElement();
		int ID = 0;
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList aid = u.getElementsByTagName("reviewID");
				int maxID = Integer.parseInt(aid.item(0).getTextContent());
				if (maxID > ID){
					ID = maxID;
				}
			}
		}
		ID = ID + 1;
		rw.setReviewID(String.valueOf(ID));
		Review return_value = new Review();
		return_value = rw;
		
		Element newrw = doc.createElement("review");
		
		Element reviewID = doc.createElement("reviewID");
		reviewID.setTextContent(rw.getReviewID());
		newrw.appendChild(reviewID);
		
		Element appID = doc.createElement("appID");
		appID.setTextContent(rw.getAppID());
		newrw.appendChild(appID);
		
		Element reviewerID = doc.createElement("reviewerID");
		reviewerID.setTextContent(rw.getReviewerID());
		newrw.appendChild(reviewerID);
		
		Element comments = doc.createElement("comments");
		comments.setTextContent(rw.getComments());
		newrw.appendChild(comments);
		
		Element decision = doc.createElement("decision");
		decision.setTextContent(rw.getDecision());
		newrw.appendChild(decision);
		
		docRoot.appendChild(newrw);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			System.out.println("###");
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
	
	public Review updateReview(Review rw) throws TransformerException {
		Review return_value = new Review();
		return_value = rw;
		String id = rw.getReviewID();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		e.getElementsByTagName("reviewerID").item(0).setTextContent(rw.getReviewerID());
		e.getElementsByTagName("comments").item(0).setTextContent(rw.getComments());
		e.getElementsByTagName("decision").item(0).setTextContent(rw.getDecision());
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
	
	public Review getOneReview(String id) {
		Review return_value = new Review();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		return_value.setReviewID(e.getElementsByTagName("reviewID").item(0).getTextContent());
		return_value.setAppID(e.getElementsByTagName("appID").item(0).getTextContent());
		return_value.setReviewerID(e.getElementsByTagName("reviewerID").item(0).getTextContent());
		return_value.setComments(e.getElementsByTagName("comments").item(0).getTextContent());
		return_value.setDecision(e.getElementsByTagName("decision").item(0).getTextContent());
		return return_value;
	}
	
	public ArrayList<Review> getAllReviews() {
		ArrayList<Review> rwlist = new ArrayList<Review>();
		Element docRoot = doc.getDocumentElement();
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			Review rw = new Review();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				rw.setReviewID(e.getElementsByTagName("reviewID").item(0).getTextContent());
				rw.setAppID(e.getElementsByTagName("appID").item(0).getTextContent());
				rw.setReviewerID(e.getElementsByTagName("reviewerID").item(0).getTextContent());
				rw.setComments(e.getElementsByTagName("comments").item(0).getTextContent());
				rw.setDecision(e.getElementsByTagName("decision").item(0).getTextContent());
				rwlist.add(rw);
			}
		}
		return rwlist;
	}
	
}