package com.benbillion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    JavaMailSender mailSender;

    public String sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("digeratees@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        return "Mail sent successfully";
    }
}
