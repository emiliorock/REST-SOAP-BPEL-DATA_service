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

import au.edu.unsw.soacourse.foundIT.modeler.companyProfileBean;

public class comProfileDAO {
	private static Document xmldoc;
	public static String path = "C:\\Users\\Administrator\\Desktop\\it\\9322\\workspace\\RestfulFoundITService\\src\\main\\java\\au\\edu\\unsw\\soacourse\\foundIT\\DAO\\companyProfile.xml";

	public comProfileDAO() {
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

	public void deleteCompany(String id) {

		Element company = (Element) selectSingleNode(id);

		xmldoc.getDocumentElement().removeChild(company);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void createCompany(companyProfileBean comPro) {

		// System.out.println(path);
		try {

			Element root = xmldoc.getDocumentElement();

			Element company = xmldoc.createElement("company");
			// user.setAttribute("id", "003");
			Element id = xmldoc.createElement("id");
			id.setTextContent(comPro.getProfileID());
			company.appendChild(id);

			Element detail = xmldoc.createElement("detail");
			detail.setTextContent(comPro.getDetail());
			company.appendChild(detail);

			Element skill = xmldoc.createElement("address");
			skill.setTextContent(comPro.getAddress());
			company.appendChild(skill);

			Element exp = xmldoc.createElement("phone");
			exp.setTextContent(comPro.getPhone());
			company.appendChild(exp);

			root.appendChild(company);
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
		String express = "//companys/company[id='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void updateCompany(companyProfileBean comPro) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String id = comPro.getProfileID();
			System.out.println(id);

			Element per = (Element) selectSingleNode(id);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("detail").item(0).setTextContent(comPro.getDetail());

			per.getElementsByTagName("address").item(0).setTextContent(comPro.getAddress());

			per.getElementsByTagName("phone").item(0).setTextContent(comPro.getPhone());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<companyProfileBean> getAllCompany() {
		// initDom(path);
		NodeList sonlist = null;
		List<companyProfileBean> resultList = new ArrayList<companyProfileBean>();
		// String path=this.getClass().getResource("Users.xml").getPath();

		try {
			sonlist = ((org.w3c.dom.Document) xmldoc).getElementsByTagName("company");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < sonlist.getLength(); i++) {
			Element son = (Element) sonlist.item(i);
			companyProfileBean bean = new companyProfileBean();
			for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling())
				// int counter=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					System.out.println(name);
					if (name != null) {
						if (name.equals("id")) {
							bean.setProfileID(node.getFirstChild().getNodeValue());
						}
						if (name.equals("detail")) {
							if (node.getFirstChild() != null) {
								bean.setDetail(node.getFirstChild().getNodeValue());
							} else {
								bean.setDetail(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("address")) {
							if (node.getFirstChild() != null) {
								bean.setAddress(node.getFirstChild().getNodeValue());
							} else {

								bean.setAddress(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("phone")) {
							if (node.getFirstChild() != null) {
								bean.setPhone(node.getFirstChild().getNodeValue());
							} else {
								bean.setPhone(".....incomplete");
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
				// System.out.println(name + " : " + value);
			}
		}

		return user;
	}

}
