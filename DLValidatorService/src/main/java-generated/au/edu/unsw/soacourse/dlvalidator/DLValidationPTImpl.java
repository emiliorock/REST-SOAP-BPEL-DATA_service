package au.edu.unsw.soacourse.dlvalidator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.edu.unsw.soacourse.dldefinitions.ObjectFactory;
import au.edu.unsw.soacourse.dldefinitions.ValidationType;
import au.edu.unsw.soacourse.dldefinitions.DLInputType;

@WebService(endpointInterface = "au.edu.unsw.soacourse.dlvalidator.DLValidationPT")
public class DLValidationPTImpl implements DLValidationPT {

	@Override
	public ValidationType validate(DLInputType dlreq) {
		// TODO Auto-generated method stub
		ObjectFactory factory = new ObjectFactory();

		ValidationType res = factory.createValidationType();

		DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
		Document doc = null;
		
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			File f = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "sample_dl.xml");
	//		System.out.println(f.toString());
			DocumentBuilder builder = fct.newDocumentBuilder();
            doc = builder.parse(f);
		}catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e){
			e.printStackTrace();
		}	
		
		Element docRoot = doc.getDocumentElement();
	//	System.out.println(docRoot.getTagName());

		ArrayList<Element> nodelist = new ArrayList<Element>();

		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				nodelist.add((Element) node);
			}
		}

		boolean found = false;
		for (Element e : nodelist) {
			NodeList n = e.getElementsByTagName("dl");
			for (int i = 0; i < n.getLength(); i++) {
				Node node = n.item(i);
				if (node.getTextContent().equals(dlreq.getDl())) {
				//	System.out.println(node.getTextContent());
					found = true;
					break;
				}
			}
		}

		if (found) {
			res.setAccept("yes");
		} else
			res.setAccept("no");
		return res;
	}

}
