/**
 * This is a simple application that will send an example email. Change to 
 * variables to your own information and uncomment code if you want the 
 * optional stuff. 
 * For gmail, go to https://myaccount.google.com/lesssecureapps?pli=1
 * and turn ON  access for less secure apps. Make sure you have the javax.mail.jar
 * in your libraries and that it is added to your build path.
 * 
 * @author Jacky Fong
 */

import java.util.Properties;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;

public class Main {

	public static void main(String[] args) {
		String  to, subject = null, from = null, 
			cc = null, bcc = null;
		String mailhost = null;
		String mailer = "msgsend"; //dont touch
		String user = null;
		String port = null;
		
		//optional stuff, ignore for presentation
		String protocol = null; //for recording mail
		String host = null; //for recording mail
		String record = null; //record mail
		String  url = null; //url to store email
		String file = null; //file attachment
		
		to = "example@uga.edu";
		user = "example@gmail.com";
		mailhost = "smtp.gmail.com";
		subject = "Test Mail";
		from = user;
	    port = "465";
		
		    

		try {
		    
		    Properties props = System.getProperties();
		   
		    //properties for general purpose, dont worry too much about this
		    props.put("mail.smtp.host", mailhost);
		    props.put("mail.smtp.user", user);
		    props.put("mail.smtp.port", port);
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.socketFactory.port", port);
		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    props.put("mail.smtp.socketFactory.fallback", "false");
		    
		    // Get a Session object
		    Authenticator auth = new SMTPAuthenticator(); //login stuff
		    Session session = Session.getInstance(props, auth);
		    
		    //Start assemblimg message
		    Message msg = new MimeMessage(session);
		    //from
			msg.setFrom(new InternetAddress(from));
		    //to
		    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false)); //to parse multiple addresses
		    //cc
		    if (cc != null){
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
		    }
		    //bcc
		    if (bcc != null){
			msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
		    }
		    //subject
		    msg.setSubject(subject);
		    //body
		    String text = "Test body. Hello world!";
		    
		    //for file attachments, remove multi-line comment
		    
		    /*if (file != null) {
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(text);
			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.attachFile(file);
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			msg.setContent(mp);
		    } else { */
			msg.setText(text);
		    //}

			//dont touch this stuff
		    msg.setHeader("X-Mailer", mailer);
		    msg.setSentDate(new Date());

		    //Sending
		    Transport.send(msg);

		    System.out.println("\nMail was sent.");
		    /*
		    if (record != null) {
			// Get a Store object
			Store store = null;
			if (url != null) {
			    URLName urln = new URLName(url);
			    store = session.getStore(urln);
			    store.connect();
			} else {
			    if (protocol != null)		
				store = session.getStore(protocol);
			    else
				store = session.getStore();

			    // Connect
			    if (host != null || user != null || password != null)
				store.connect(host, user, password);
			    else
				store.connect();
			}

			// Get record Folder.  Create if it does not exist.
			Folder folder = store.getFolder(record);
			if (folder == null) {
			    System.err.println("Can't get record folder.");
			    System.exit(1);
			}
			if (!folder.exists())
			    folder.create(Folder.HOLDS_MESSAGES);

			Message[] msgs = new Message[1];
			msgs[0] = msg;
			folder.appendMessages(msgs);

			System.out.println("Mail was recorded successfully.");
		    }
	*/
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}

class SMTPAuthenticator extends javax.mail.Authenticator{
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication("example@gmail.com", "password");
		//make sure the username is the same as above
	}
}