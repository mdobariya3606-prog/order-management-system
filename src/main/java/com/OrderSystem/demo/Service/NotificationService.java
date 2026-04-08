package com.OrderSystem.demo.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class NotificationService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value(("${twilio.auth.token}"))
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String whatsappFrom;

    @Value("${twilio.sms.from}")
    private String smsFrom;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSms(String to, String message) {
        try {
            Twilio.init(accountSid, authToken);
            Message messageObj = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(smsFrom),
                    message
            ).create();

            System.out.println("SMS Sent to: " + to);
        } catch (Exception e) {
            throw new RuntimeException("SMS Sending Failed !!", e);
        }
    }

    public void sendWhatsapp (String to, String message) {
        try {
            Twilio.init(accountSid, authToken);
            Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber(whatsappFrom),
                    message
            ).create();

            System.out.println("Whatsapp Message Sent to: " + to);
        } catch (Exception e) {
            throw new RuntimeException("Whatsapp Message Sending Failed !!", e);
        }
    }

    public void sendEmail(String toEmail, String filePath){
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Current Stock Details.");
            helper.setText("Attached is the latest stock report.");

            File fileObj = new File(filePath);

            if (!fileObj.exists() || fileObj.length() == 0) {
                throw new RuntimeException("CSV file missing or empty");
            }

            FileSystemResource file = new FileSystemResource(new File(filePath));

            helper.addAttachment("currentStock.csv", file);
            mailSender.send(message);

            System.out.println("Mail sent to: " + toEmail);
        } catch (Exception e) {
            throw new RuntimeException("Email Generation Failed !!", e);
        }
    }
}