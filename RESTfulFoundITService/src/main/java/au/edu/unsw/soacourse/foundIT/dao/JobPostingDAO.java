package au.edu.unsw.soacourse.foundIT.dao;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import au.edu.unsw.soacourse.foundIT.model.JobPosting;
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

public class JobPostingDAO {

	private Document doc;
	private String path;
	private String localpath ="C:\\User\\meng\\Desktop\\COMP9322\\assn2\\RESTfulFoundITService\\src\\main\\webapp\\WEB-INF\\jobPostings.xml";
	File file;
	File localFile = new File(localpath);
	
	public JobPostingDAO() {
		init();
	}

	public void init(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			file = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "jobPostings.xml");
			//System.out.println(f.toString());
			path = file.toString();
			DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
		}catch (SAXException | IOException | ParserConfigurationException | URISyntaxException e){
			e.printStackTrace();
		}		
	}
	
	public JobPosting createJob(JobPosting job){
		Element docRoot = doc.getDocumentElement();
		int ID = 0;
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element u = (Element) node;
				NodeList jid = u.getElementsByTagName("jobID");
				int maxID = Integer.parseInt(jid.item(0).getTextContent());
				if (maxID > ID){
					ID = maxID;
				}
			}
		}
		ID = ID + 1;
		
		Element newJob = doc.createElement("job");
		JobPosting return_value = new JobPosting();
		
		Element jobID = doc.createElement("jobID");
		jobID.setTextContent(String.valueOf(ID));
		newJob.appendChild(jobID);
		return_value.setJobID(job.getJobID()); 
		
		Element companyID = doc.createElement("companyID");
		companyID.setTextContent(job.getCompanyID());
		newJob.appendChild(companyID);
		return_value.setCompanyID(job.getCompanyID());
		
		Element companyName = doc.createElement("companyName");
		companyName.setTextContent(job.getCompanyName());
		newJob.appendChild(companyName);
		return_value.setCompanyName(job.getCompanyName());
		
		Element salaryRate = doc.createElement("salaryRate");
		salaryRate.setTextContent(job.getSalaryRate());
		newJob.appendChild(salaryRate);
		return_value.setSalaryRate(job.getSalaryRate());
		
		Element positionType = doc.createElement("positionType");
		positionType.setTextContent(job.getPositionType());
		newJob.appendChild(positionType);
		return_value.setPositionType(job.getPositionType());
		
		Element location = doc.createElement("location");
		location.setTextContent(job.getLocation());
		newJob.appendChild(location);
		return_value.setLocation(job.getLocation());
		
		Element description = doc.createElement("description");
		description.setTextContent(job.getDescription());
		newJob.appendChild(description);
		return_value.setDescription(job.getDescription());
		
		Element status = doc.createElement("status");
		status.setTextContent(job.getStatus());
		newJob.appendChild(status);
		return_value.setStatus(job.getStatus());
		
		docRoot.appendChild(newJob);
		
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

	public boolean deleteJob(String id) {
		Element j = (Element)findByID(id);
		if (j == null) return false;
		doc.getDocumentElement().removeChild(j);
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
	
	public JobPosting updateJob(JobPosting job) throws TransformerException {
		JobPosting return_value = new JobPosting();
		return_value = job;
		String id = job.getJobID();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		e.getElementsByTagName("salaryRate").item(0).setTextContent(job.getSalaryRate());
		e.getElementsByTagName("positionType").item(0).setTextContent(job.getPositionType());
		e.getElementsByTagName("location").item(0).setTextContent(job.getLocation());
		e.getElementsByTagName("description").item(0).setTextContent(job.getDescription());
		e.getElementsByTagName("status").item(0).setTextContent(job.getStatus());
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
	
	public JobPosting getOneJob(String id) {
		JobPosting return_value = new JobPosting();
		Element e = (Element)findByID(id);
		if (e == null) return null;
		return_value.setJobID(id);
		return_value.setCompanyID(e.getElementsByTagName("companyID").item(0).getTextContent());
		return_value.setCompanyName(e.getElementsByTagName("companyName").item(0).getTextContent());
		return_value.setSalaryRate(e.getElementsByTagName("salaryRate").item(0).getTextContent());
		return_value.setPositionType(e.getElementsByTagName("positionType").item(0).getTextContent());
		return_value.setLocation(e.getElementsByTagName("location").item(0).getTextContent());
		return_value.setDescription(e.getElementsByTagName("description").item(0).getTextContent());
		return_value.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
		return return_value;
	}
	
	public ArrayList<JobPosting> getAllJobs() {
		ArrayList<JobPosting> joblist = new ArrayList<JobPosting>();
		Element docRoot = doc.getDocumentElement();
		for (Node node = docRoot.getFirstChild(); node != null; node = node.getNextSibling()) {
			JobPosting job = new JobPosting();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				job.setJobID(e.getElementsByTagName("jobID").item(0).getTextContent());
				job.setCompanyID(e.getElementsByTagName("companyID").item(0).getTextContent());
				job.setCompanyName(e.getElementsByTagName("companyName").item(0).getTextContent());
				job.setSalaryRate(e.getElementsByTagName("salaryRate").item(0).getTextContent());
				job.setPositionType(e.getElementsByTagName("positionType").item(0).getTextContent());
				job.setLocation(e.getElementsByTagName("location").item(0).getTextContent());
				job.setDescription(e.getElementsByTagName("description").item(0).getTextContent());
				job.setStatus(e.getElementsByTagName("status").item(0).getTextContent());
				joblist.add(job);
			}
		}
		return joblist;
	}
	
	public Node findByID(String id) {
		Node result = null;
		Element root = doc.getDocumentElement();
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String str = "//jobPostings/job[jobID='" + id + "']";
		try {
			result = (Node) xpath.evaluate(str, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}