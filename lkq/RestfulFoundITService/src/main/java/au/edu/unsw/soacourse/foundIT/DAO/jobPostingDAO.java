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

import au.edu.unsw.soacourse.foundIT.modeler.jobPostingBean;

public class jobPostingDAO {

	// String filepath=this.getClass().getResource("userProfile").getPath();
	private static Document xmldoc;
	public static String path = "/Users/air/Desktop/RestfulFoundITService/src/main/java/au/edu/unsw/soacourse/foundIT/DAO/jobPosting.xml";

	public jobPostingDAO() {
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

	public void deletePosting(String id) {

		Element post = (Element) selectSingleNode(id);

		xmldoc.getDocumentElement().removeChild(post);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void createPosting(jobPostingBean jobPosting) {

		// System.out.println(path);
		try {

			Element root = xmldoc.getDocumentElement();

			Element posting = xmldoc.createElement("posting");
			// user.setAttribute("id", "003");
			Element id = xmldoc.createElement("id");
			id.setTextContent(jobPosting.getId());
			posting.appendChild(id);

			Element comLink = xmldoc.createElement("comLink");
			comLink.setTextContent(jobPosting.getComLink());
			posting.appendChild(comLink);

			Element salaryRate = xmldoc.createElement("salaryRate");
			salaryRate.setTextContent(jobPosting.getSalaryRate());
			posting.appendChild(salaryRate);

			Element positionType = xmldoc.createElement("positionType");
			positionType.setTextContent(jobPosting.getPositionType());
			posting.appendChild(positionType);

			Element location = xmldoc.createElement("location");
			location.setTextContent(jobPosting.getLocation());
			posting.appendChild(location);

			Element description = xmldoc.createElement("description");
			description.setTextContent(jobPosting.getDescription());
			posting.appendChild(description);

			Element status = xmldoc.createElement("status");
			status.setTextContent(jobPosting.getStatus());
			posting.appendChild(status);

			root.appendChild(posting);
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
		String express = "//postings/posting[id='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void updatePosting(jobPostingBean jobPost) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String id = jobPost.getId();
			System.out.println(id);

			Element per = (Element) selectSingleNode(id);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("comLink").item(0).setTextContent(jobPost.getComLink());

			per.getElementsByTagName("salaryRate").item(0).setTextContent(jobPost.getSalaryRate());

			per.getElementsByTagName("positionType").item(0).setTextContent(jobPost.getPositionType());

			per.getElementsByTagName("location").item(0).setTextContent(jobPost.getLocation());

			per.getElementsByTagName("description").item(0).setTextContent(jobPost.getDescription());

			per.getElementsByTagName("status").item(0).setTextContent(jobPost.getStatus());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<jobPostingBean> getAllPostings() {
		// initDom(path);
		NodeList sonlist = null;
		List<jobPostingBean> resultList = new ArrayList<jobPostingBean>();
		// String path=this.getClass().getResource("Users.xml").getPath();

		try {
			sonlist = ((org.w3c.dom.Document) xmldoc).getElementsByTagName("posting");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < sonlist.getLength(); i++) {
			Element son = (Element) sonlist.item(i);
			jobPostingBean bean = new jobPostingBean();
			for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling())
				// int counter=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					//System.out.println(name);
					if (name != null) {
						if (name.equals("id")) {
							bean.setId(node.getFirstChild().getNodeValue());
						}
						if (name.equals("comLink")) {
							if (node.getFirstChild() != null) {
								bean.setComLink(node.getFirstChild().getNodeValue());
							} else {
								bean.setComLink(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("salaryRate")) {
							if (node.getFirstChild() != null) {
								bean.setSalaryRate(node.getFirstChild().getNodeValue());
							} else {

								bean.setSalaryRate(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("positionType")) {
							if (node.getFirstChild() != null) {
								bean.setPositionType(node.getFirstChild().getNodeValue());
							} else {
								bean.setPositionType(".....incomplete");
							}

						}
						if (name.equals("location")) {
							if (node.getFirstChild() != null) {
								bean.setLocation(node.getFirstChild().getNodeValue());
							} else {
								bean.setLocation(".....incomplete");
							}

						}
						if (name.equals("description")) {
							if (node.getFirstChild() != null) {
								bean.setDescription(node.getFirstChild().getNodeValue());
							} else {
								bean.setDescription(".....incomplete");
							}
						}
						if (name.equals("status")) {
							if (node.getFirstChild() != null) {
								bean.setStatus(node.getFirstChild().getNodeValue());
							} else {
								bean.setStatus(".....incomplete");
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

		for (Node node = user.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String name = node.getNodeName();
				String value = node.getFirstChild().getNodeValue();
				System.out.println(name + " : " + value);
			}
		}

		return user;
	}

}
