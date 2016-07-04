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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.edu.unsw.soacourse.foundIT.model.CompanyProfile;

public class CompanyProfileDAO {
	private Document doc;
	private String path;
	private String localpath = "C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\companyProfiles.xml";
	File f;
	File localFile = new File(localpath);

	public CompanyProfileDAO() {
		init();
	}

	public void init() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			f = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "companyProfiles.xml");
			// System.out.println(f.toString());
			path = f.toString();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
		} catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public CompanyProfile createCompanyProfile(CompanyProfile cp) {
		Element docRoot = doc.getDocumentElement();
		CompanyProfile cpBean = cp;
		int ID = 0;

		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element comP = (Element) node;
				NodeList companyIDN = comP.getElementsByTagName("companyID");
				int maxID = Integer.parseInt(companyIDN.item(0).getTextContent());
				if (maxID > ID) {
					ID = maxID;
				}
			}
		}
		ID = ID + 1;

		Element newCompanyProfile = doc.createElement("companyProfile");

		Element companyID = doc.createElement("companyID");
		companyID.setTextContent(String.valueOf(ID));
		newCompanyProfile.appendChild(companyID);

		Element managerID = doc.createElement("managerID");
		managerID.setTextContent(cp.getManagerID());
		newCompanyProfile.appendChild(managerID);

		Element name = doc.createElement("name");
		name.setTextContent(cp.getName());
		newCompanyProfile.appendChild(name);

		Element url = doc.createElement("url");
		url.setTextContent(cp.getUrl());
		newCompanyProfile.appendChild(url);

		Element intro = doc.createElement("intro");
		intro.setTextContent(cp.getIntro());
		newCompanyProfile.appendChild(intro);

		Element img = doc.createElement("img");
		img.setTextContent(cp.getImg());
		newCompanyProfile.appendChild(img);

		docRoot.appendChild(newCompanyProfile);

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

		cpBean.setCompanyID(String.valueOf(ID));
		return cpBean;
	}

	public CompanyProfile updateCompanyProfile(CompanyProfile cp) {
		CompanyProfile cpBean = cp;
		Element element = findByCompanyID(cp.getCompanyID());

		element.getElementsByTagName("name").item(0).setTextContent(cp.getName());
		element.getElementsByTagName("url").item(0).setTextContent(cp.getUrl());
		element.getElementsByTagName("intro").item(0).setTextContent(cp.getIntro());
		element.getElementsByTagName("img").item(0).setTextContent(cp.getImg());
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

		return cpBean;
	}

	public CompanyProfile getCompanyProfile(String companyID) {
		CompanyProfile cp = new CompanyProfile();
		Element element = findByCompanyID(companyID);
		if (element == null) {
			return null;
		} else {
			cp.setCompanyID(element.getElementsByTagName("companyID").item(0).getTextContent());
			cp.setManagerID(element.getElementsByTagName("managerID").item(0).getTextContent());
			cp.setName(element.getElementsByTagName("name").item(0).getTextContent());
			cp.setUrl(element.getElementsByTagName("url").item(0).getTextContent());
			cp.setIntro(element.getElementsByTagName("intro").item(0).getTextContent());
			cp.setImg(element.getElementsByTagName("img").item(0).getTextContent());
			return cp;
		}

	}

	public boolean deleteCompanyProfile(String companyID) {
		Element element = findByCompanyID(companyID);
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

	public String managerIDToCompanyID(String managerID) {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String expr = "//companyProfiles/companyProfile[managerID='" + managerID + "']";
		Element element = null;
		String companyID = null;
		try {
			element = (Element) xpath.evaluate(expr, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (element == null) {
			return null;
		} else {
			companyID = element.getElementsByTagName("companyID").item(0).getTextContent();
			return companyID;
		}
	}

	public Element findByCompanyID(String companyID) {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String expr = "//companyProfiles/companyProfile[companyID='" + companyID + "']";
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
