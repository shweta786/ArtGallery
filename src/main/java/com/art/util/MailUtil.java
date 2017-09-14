/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.art.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author SHWETA
 */
public class MailUtil {

    public static void sendAttachmentEmail(Session session, String toEmail, String subject, String body, List<String> fileNames) {

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("galleryart1010@gmail.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("galleryart1010@gmail.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is attachment
            for(String fn:fileNames){
                DataSource source = new FileDataSource("C:/Users/SHWETA/Desktop/" + fn);
                messageBodyPart = new MimeBodyPart();        
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fn);
                multipart.addBodyPart(messageBodyPart);               
            }
            
            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);

        } catch (MessagingException e) {
            System.out.println("message exception!!  "+e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("encoding exception!!  "+e);
        }

    }

}
