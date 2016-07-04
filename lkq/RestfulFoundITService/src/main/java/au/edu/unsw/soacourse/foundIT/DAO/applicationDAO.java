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

import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;

public class applicationDAO {

	// String filepath=this.getClass().getResource("userProfile").getPath();
	private static Document xmldoc;
	public static String path = "C:\\Users\\Administrator\\Desktop\\it\\9322\\workspace\\RestfulFoundITService\\src\\main\\java\\au\\edu\\unsw\\soacourse\\foundIT\\DAO\\application.xml";

	public applicationDAO() {
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

	public void deleteApplication(String id) {

		Element app = (Element) selectSingleNode(id);

		xmldoc.getDocumentElement().removeChild(app);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void createApplication(applicationBean application) {

		// System.out.println(path);
		try {

			Element root = xmldoc.getDocumentElement();

			Element app = xmldoc.createElement("app");
			// user.setAttribute("id", "003");
			Element id = xmldoc.createElement("id");
			id.setTextContent(application.getId());
			app.appendChild(id);

			Element link = xmldoc.createElement("link");
			link.setTextContent(application.getLink());
			app.appendChild(link);

			Element coverLetter = xmldoc.createElement("coverLetter");
			coverLetter.setTextContent(application.getCoverLetter());
			app.appendChild(coverLetter);

			Element status = xmldoc.createElement("status");
			status.setTextContent(application.getStatus());
			app.appendChild(status);

			root.appendChild(app);
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
		String express = "//applications/application[id='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void updateApplication(applicationBean app) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String id = app.getId();
			//System.out.println(id);

			Element per = (Element) selectSingleNode(id);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("link").item(0).setTextContent(app.getLink());

			per.getElementsByTagName("coverLetter").item(0).setTextContent(app.getCoverLetter());

			per.getElementsByTagName("status").item(0).setTextContent(app.getStatus());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<applicationBean> getAllApplications() {
		// initDom(path);
		NodeList sonlist = null;
		List<applicationBean> resultList = new ArrayList<applicationBean>();
		// String path=this.getClass().getResource("Users.xml").getPath();

		try {
			sonlist = ((org.w3c.dom.Document) xmldoc).getElementsByTagName("application");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < sonlist.getLength(); i++) {
			Element son = (Element) sonlist.item(i);
			applicationBean bean = new applicationBean();
			for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling())
				// int counter=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					System.out.println(name);
					if (name != null) {
						if (name.equals("id")) {
							bean.setId(node.getFirstChild().getNodeValue());
						}
						if (name.equals("link")) {
							if (node.getFirstChild() != null) {
								bean.setLink(node.getFirstChild().getNodeValue());
							} else {
								bean.setLink(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("coverLetter")) {
							if (node.getFirstChild() != null) {
								bean.setCoverLetter(node.getFirstChild().getNodeValue());
							} else {

								bean.setCoverLetter(".....incomplete");
							}
							// counter++;
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
