package com.arademia.aqar.util;
import com.arademia.aqar.config.model.ResponseMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
public class MailSenderUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String recipient, String recipientName, String subject, String body) throws MailException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = parseEmailBody(body,recipientName);
            helper.setText(htmlMsg, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setFrom("admin@clavitag.com","CLAVITAG Team");
            javaMailSender.send(mimeMessage);
            //javaMailSender.send(msg);
        }catch (MailException e) {
            e.printStackTrace();
        } catch (Exception exp){
            exp.printStackTrace();
        }
    }

    /*
    public void sendEmailWithAttachment(String recipient, String subject, String body) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(recipient);
        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }*/

    private String parseEmailBody(String msg,String username){
        try {
            String fileName = "src/main/resources/templates/emailTemplate1.html";
            Document doc = Jsoup.parse(new File(fileName), "utf-8");
            Element bodyElement = doc.selectFirst("#body");
            bodyElement.text(msg);
            Element usernameElement = doc.selectFirst("#username");
            usernameElement.text("Hi "+username+",");
            return doc.toString();
        }catch (Exception e){
            return "Parse method failed";
        }
    }
}
