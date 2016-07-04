package au.edu.unsw.soacourse.adrvalidator;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import au.edu.unsw.soacourse.adrdefinitions.ObjectFactory;
import au.edu.unsw.soacourse.adrdefinitions.ValidationType;
import au.edu.unsw.soacourse.adrdefinitions.ADRInputType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

@WebService(endpointInterface = "au.edu.unsw.soacourse.adrvalidator.ADRValidationPT")
public class ADRValidationPTImpl implements ADRValidationPT {

	@Override
	public ValidationType validate(ADRInputType adrreq) {
		// TODO Auto-generated method stub
		ObjectFactory factory = new ObjectFactory();

		ValidationType res = factory.createValidationType();

		DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
		Document doc = null;
		
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			File f = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "sample_address.xml");
	//		System.out.println(f.toString());
			DocumentBuilder builder = fct.newDocumentBuilder();
            doc = builder.parse(f);
		}catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e){
			e.printStackTrace();
		}	
		
		Element docRoot = doc.getDocumentElement();

//		System.out.println(docRoot.getTagName());

		ArrayList<Element> nodelist = new ArrayList<Element>();

		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				nodelist.add((Element) node);
			}
		}

		boolean found = false;
		for (Element e : nodelist) {
			NodeList n = e.getElementsByTagName("address");
			for(int i=0; i < n.getLength(); i++){
				Node node = n.item(i);
				if (node.getTextContent().equals(adrreq.getAdr())){
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
