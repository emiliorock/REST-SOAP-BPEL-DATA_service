package au.edu.unsw.soacourse.foundIT.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import au.edu.unsw.soacourse.foundIT.email.MyAuthenticator;

public class MailDAO {

	@SuppressWarnings("resource")
	public void sendRSS(String keyword, String sort_by, String email) throws IOException, TransformerException, URISyntaxException {
		
		File myClass = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
		File job = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "job.xsl");
		File new_job = new File(catalinaBase + "/webapps/ROOT/" + "new_job.xsl");
		File jobAlerts = new File(myClass.getCanonicalPath().replaceFirst("classes", "") + "jobAlerts.xml");
		File output = new File(catalinaBase + "/webapps/ROOT/" + "output.xml");
	
		System.out.println("job:"+job.toString());
		System.out.println("new_job:"+new_job.toString());
		System.out.println("jobAlerts:"+jobAlerts.toString());
		System.out.println("output:"+output.toString());
		
		
		
		BufferedReader reader = new BufferedReader(new FileReader(job));
		BufferedWriter writer = new BufferedWriter(new FileWriter(new_job));
		String inString;

		keyword = keyword.replaceAll("\\s+", "&#x20;");
		System.out.println(keyword);

		while ((inString = reader.readLine()) != null) {
			if (!sort_by.equals("") && inString.contains("<xsl:sort ")) {
				inString = "<xsl:sort select=\"" + sort_by + "\" />";
			}
			if (!keyword.equals("") && inString.contains("<xsl:if test=\"contains(title")) {
				inString = "<xsl:if test=\"contains(title, '" + keyword + "')\">";
			}
			if (!keyword.equals("") && inString.contains("<xsl:if test=\"contains(description")) {
				inString = "<xsl:if test=\"contains(description, '" + keyword + "')\">";
			}
			writer.write(inString + "\n");
		}
		reader.close();
		writer.close();

		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(new_job);
		Transformer transformer = factory.newTransformer(xslt);

		Source text = new StreamSource(jobAlerts);
		transformer.transform(text, new StreamResult(output));

		inString = "";
		String new_rss_filename = getRandomFilename(7);
		System.out.println(new_rss_filename);

		
		File rss_file = new File(catalinaBase + "/webapps/ROOT/" + new_rss_filename);

		reader = new BufferedReader(new FileReader(output));
		writer = new BufferedWriter(new FileWriter(rss_file));

		int count = 0;
		while ((inString = reader.readLine()) != null) {
			count++;
		}

		if (count == 1) {
			reader.close();
			writer.close();
			MyAuthenticator sender = new MyAuthenticator();
			sender.rssEmail(email, keyword, 0);
			System.out.println("No results. Job Feed has been sent");
		}

		else {
			reader = new BufferedReader(new FileReader(output));
			inString = "";
			writer.write(
					"<rss version=\"2.0\"><channel><title>Job Search Results</title><description>RSS Feed for FoundIT Job Search</description><language>en-us</language><docs>http://backend.userland.com/rss</docs><generator>RSS.NET: http://www.rssdotnet.com/</generator>\n");
			while ((inString = reader.readLine()) != null) {
				if (inString.contains("<?xml")) {
					inString = "<link>";
				}
				if (inString.contains("<link>")) {
					inString = inString.replaceAll("\\<link\\>", "\\<item\\>\\<link\\>");
				}
				if (inString.contains("</description>")) {
					inString = inString.replaceAll("\\</description\\>", "\\</description\\>\\</item\\>");
				}
				writer.write(inString + "\n");
			}
			writer.write("</channel></rss>");
			reader.close();
			writer.close();

			MyAuthenticator sender = new MyAuthenticator();
			sender.rssEmail(email, new_rss_filename, 1);
			System.out.println("Job Feed has been sent");
		}
	}

	public String getRandomFilename(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		sb.append(".xml");
		return sb.toString();
	}

}