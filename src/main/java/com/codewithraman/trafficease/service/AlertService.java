package com.codewithraman.trafficease.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class AlertService {

    @Value("${twilio.accountSid}")
    private String accountSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.fromPhone}")
    private String fromPhone;
    @Value("${twilio.toPhone}")
    private String toPhone;

    private final JavaMailSender mailSender;

    public AlertService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSms(String message) {
        try {
            Twilio.init(accountSid, authToken);
            Message.creator(new com.twilio.type.PhoneNumber(toPhone),
                            new com.twilio.type.PhoneNumber(fromPhone),
                            message).create();
            System.out.println("Twilio SMS sent: " + message);
        } catch (Exception e) {
            System.err.println("Twilio send failed: " + e.getMessage());
        }
    }

    public void sendEmail(String subject, String body, String toEmail) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(toEmail);
            mail.setSubject(subject);
            mail.setText(body);
            mailSender.send(mail);
            System.out.println("Email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Mail send failed: " + e.getMessage());
        }
    }
}