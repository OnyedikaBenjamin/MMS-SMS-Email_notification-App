package com.benbillion.services;

import com.benbillion.dtos.OTPVerificationRequest;
import com.benbillion.models.data.Email;
import com.benbillion.models.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender,
                                  EmailRepository emailRepository
    ) {
        this.javaMailSender = javaMailSender;
        this.emailRepository=emailRepository;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    @Async          //  wait for the OTpMail to be sent before any other execution
    @Override
    public String sendCreatedTodoMail() {
        try {
            Email existingEmail = emailRepository.findAll().get(0);
            Email email = new Email();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
            message.setTo(existingEmail.getReminderEmail());
            message.setSubject("CREATED SUCCESSFULLY");
//            message.setText(buildEmail(), true);
            message.setText("Your TODO have been created successfully. You might now" +
                            "You will get similar mails for imminent tasks you wanna execute");
            message.setFrom("digeratees@gmail.com");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException | MailException e) {
            log.info("problem2: ");
            log.info(e.getMessage());
            throw new RuntimeException("Please add an email for reminder");
        }
        return "successful";
    }

    @Async
    @Override
    public void sendReminderMail(String text) {
        try {
            Email existingEmail = emailRepository.findAll().get(0);
            Email email = new Email();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
            message.setTo(existingEmail.getReminderEmail());
            message.setSubject("Task Reminder");
//            message.setText(buildEmail(), true);
            message.setText(text);
            message.setFrom("digeratees@gmail.com");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException | MailException e) {
            log.info("problem2: ");
            log.info(e.getMessage());
            throw new RuntimeException("Please add an email for reminder");
        }
    }



    @Async
    @Override
    public void sendOTPVerificationMail(OTPVerificationRequest otpVerificationRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
            message.setTo(otpVerificationRequest.getReminderEmail());
            message.setSubject("Your One Time Password");
//            message.setText(buildEmail(), true);
            message.setText(otpVerificationRequest.getBody(), true);
            message.setFrom("digeratees@gmail.com");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException | MailException e) {
            log.info("problem2: ");
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @Override
    public String registerEmail(OTPVerificationRequest createEmailRequest) {
        if (!(emailRepository.findAll().size()==0)) {
        throw new ArrayIndexOutOfBoundsException("You cannot have more than one reminder mail");}
        try {
            isValidEmail(createEmailRequest.getReminderEmail());}
        catch (MailParseException message){
            throw new RuntimeException(message);}
        return "Successful";
    }
    @Override
    public String deleteReminderEmail(){
        emailRepository.deleteAll();
        return "Successfully Deleted";
    }

    private String buildEmail() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Page Title</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        background-color: #000000;\n" +
                "        color: #00FF00;\n" +
                "      }\n" +
                "      .container {\n" +
                "        max-width: 800px;\n" +
                "        margin: 0 auto;\n" +
                "        padding: 20px;\n" +
                "      }\n" +
                "      p {\n" +
                "        font-size: 1.2em;\n" +
                "        line-height: 1.5;\n" +
                "        margin: 0;\n" +
                "        padding: 20px 0;\n" +
                "        border-bottom: 1px solid #e2e2e2;\n" +
                "      }\n" +
                "      p:last-child {\n" +
                "        border-bottom: none;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <p>Congrats Bro, you are now a billionaire</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }


}

