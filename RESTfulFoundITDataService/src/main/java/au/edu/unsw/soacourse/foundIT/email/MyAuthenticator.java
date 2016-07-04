package au.edu.unsw.soacourse.foundIT.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MyAuthenticator extends Authenticator
{
	String userName = null;
	String password = null;

	public MyAuthenticator()
	{
	}

	public MyAuthenticator(String username, String password)
	{
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(userName, password);
	}

	public void SendEmail(String ToAddr, String Content)
	{

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("587");
		mailInfo.setValidate(true);
		mailInfo.setUserName("414154656");
		mailInfo.setPassword("zpseyjhyadzlbhfi");
		mailInfo.setFromAddress("414154656@qq.com");
		mailInfo.setToAddress(ToAddr);
		mailInfo.setSubject("Item Sold");
		mailInfo.setContent(Content);

		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);
		sms.sendHtmlMail(mailInfo);
	}
	
	public void rssEmail(String email, String filename, int flag)
	{
		StringBuffer sb = new StringBuffer("Thanks for join FoundIT :-)\n");
		if (flag == 0) {
			sb.append("Sorry, unable to find keyword:");
			sb.append(filename);
		}
		else {
			sb.append("\nPlease access to your RSS via:\n");
			sb.append("\nhttp://localhost:8080/");
			sb.append(filename);
		}
		String str = sb.toString();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("587");
		mailInfo.setValidate(true);
		mailInfo.setUserName("414154656");
		mailInfo.setPassword("zpseyjhyadzlbhfi");
		mailInfo.setFromAddress("414154656@qq.com");
		mailInfo.setToAddress(email);
		mailInfo.setSubject("Your job alerts on FoundIT");
		mailInfo.setContent(str);

		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);
		sms.sendHtmlMail(mailInfo);
	}

	public void sendAttachment(String email) {
		// Recipient's email ID needs to be mentioned.
	      String to = email;

	      // Sender's email ID needs to be mentioned
	      String from = "414154656@qq.com";

	      final String username = "414154656";//change accordingly
	      final String password = "zpseyjhyadzlbhfi";//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.qq.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	            }
	         });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject("Testing Subject");

	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         messageBodyPart.setText("This is message body");

	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = "F:\\local_xml_files\\new_rss.xml";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	      } catch (MessagingException e) {
	          throw new RuntimeException(e);
	       }
	      
	}
}
