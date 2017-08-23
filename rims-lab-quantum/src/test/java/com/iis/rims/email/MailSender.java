package com.iis.rims.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
// http://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
// http://websystique.com/spring/spring-4-email-integration-tutorial/
public class MailSender {
	public static void main(String[] args) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
        
//        Properties javaMailProperties = new Properties();
//        javaMailProperties.put("mail.smtp.starttls.enable", "true");
//        javaMailProperties.put("mail.smtp.auth", "true");
//        javaMailProperties.put("mail.transport.protocol", "smtp");
//        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
        final String email = "huy1912@gmail.com",
        		username = "huy1912",
        		password = "conan2408",
        		host = "smtp.gmail.com";
		int port = 465;
        //Using gmail
//        mailSender.setHost(host);
//        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        
        Properties props = new Properties();
//		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.debug", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.socketFactory.fallback", "false");
         
        mailSender.setJavaMailProperties(props);
//        mailSender.set
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
        	 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(email);
                mimeMessage.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("hoaavtpx@yahoo.com,huy1912@yahoo.com,huy1912@gmail.com,huy.ho@quantumsystech.com"));
                mimeMessage.setText("Dear customer,"
                        + ", thank you for placing order. Your order id is #0003.");
                mimeMessage.setSubject("Your order #0003");
            }
        };
        
        mailSender.send(preparator);
	}
}
