package au.edu.unsw.soacourse.foundIT.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
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

import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;

public class reviewDAO {

	// String filepath=this.getClass().getResource("userProfile").getPath();
	private static Document xmldoc;
	public static String path = "C:\\Users\\Administrator\\Desktop\\it\\9322\\workspace\\RestfulFoundITService\\src\\main\\java\\au\\edu\\unsw\\soacourse\\foundIT\\DAO\\review.xml";

	public reviewDAO() {
		// this.getClass().getResource("userProfile.xml").getPath();
		// System.out.println(path);
		// path = this.getClass().getResource("userProfile.xml").getPath();
		// System.out.println(this.getClass().getResource("userProfile.xml").getPath());
		initDom(path);

	}

	public void initDom(String path) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// new FileInputStream(new File(xmlFileName))
			xmldoc = db.parse(new FileInputStream(new File(path)));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void deleteReview(String id) {

		Element review = (Element) selectSingleNode(id);

		xmldoc.getDocumentElement().removeChild(review);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void createReview(reviewBean review) {

		// System.out.println(path);
		try {

			Element root = xmldoc.getDocumentElement();

			Element rev = xmldoc.createElement("review");
			// user.setAttribute("id", "003");
			Element reviewId = xmldoc.createElement("reviewId");
			reviewId.setTextContent(review.getReviewId());
			rev.appendChild(reviewId);

			Element appId = xmldoc.createElement("appId");
			appId.setTextContent(review.getAppId());
			rev.appendChild(appId);

			Element uId = xmldoc.createElement("uId");
			uId.setTextContent(review.getuId());
			rev.appendChild(uId);

			Element comments = xmldoc.createElement("comments");
			comments.setTextContent(review.getComments());
			rev.appendChild(comments);

			Element decision = xmldoc.createElement("decision");
			decision.setTextContent(review.getComments());
			rev.appendChild(decision);

			root.appendChild(rev);
			// xmldoc.setTextContent("dddddd");
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			DOMSource source = new DOMSource(xmldoc);
			PrintWriter pw = new PrintWriter(new FileOutputStream(path));
			StreamResult result = new StreamResult(pw);
			former.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Node selectSingleNode(String id) {
		Node result = null;
		// userProfileBean result2=new userProfileBean();
		Element root = xmldoc.getDocumentElement();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String express = "//reviews/review[reviewId='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void updateReview(reviewBean review) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String id = review.getReviewId();
			// System.out.println(id);

			Element per = (Element) selectSingleNode(id);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("appId").item(0).setTextContent(review.getAppId());

			per.getElementsByTagName("uId").item(0).setTextContent(review.getuId());

			per.getElementsByTagName("comments").item(0).setTextContent(review.getComments());

			per.getElementsByTagName("decision").item(0).setTextContent(review.getDecision());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<reviewBean> getAllReviews() {
		// initDom(path);
		NodeList sonlist = null;
		List<reviewBean> resultList = new ArrayList<reviewBean>();
		// String path=this.getClass().getResource("Users.xml").getPath();
		System.out.println("222");
		try {
			sonlist = ((org.w3c.dom.Document) xmldoc).getElementsByTagName("review");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < sonlist.getLength(); i++) {
			Element son = (Element) sonlist.item(i);
			reviewBean bean = new reviewBean();
			for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling())
				// int counter=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					//System.out.println(name);
					if (name != null) {
						if (name.equals("reviewId")) {
							bean.setReviewId(node.getFirstChild().getNodeValue());
						}
						if (name.equals("appId")) {
							if (node.getFirstChild() != null) {
								bean.setAppId(node.getFirstChild().getNodeValue());
							} else {
								bean.setAppId(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("uId")) {
							if (node.getFirstChild() != null) {
								bean.setuId(node.getFirstChild().getNodeValue());
							} else {

								bean.setuId(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("comments")) {
							if (node.getFirstChild() != null) {
								bean.setComments(node.getFirstChild().getNodeValue());
							} else {
								bean.setComments(".....incomplete");
							}

						}
						if (name.equals("decision")) {
							if (node.getFirstChild() != null) {
								bean.setDecision(node.getFirstChild().getNodeValue());
							} else {
								bean.setDecision(".....incomplete");
							}

						}
					}
				}
			resultList.add(bean);
		}

		return resultList;
	}

	public Node queryUser(String id) {
		Node user = null;
		try {

			// Element root = xmldoc.getDocumentElement();

			user = selectSingleNode(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*for (Node node = user.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String name = node.getNodeName();
				String value = node.getFirstChild().getNodeValue();
				System.out.println(name + " : " + value);
			}
		}*/

		return user;
	}

}
