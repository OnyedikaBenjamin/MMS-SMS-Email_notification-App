package com.benbillion.services;

import com.benbillion.dtos.CreatedTodoMailRequest;
import com.benbillion.dtos.OTPVerificationRequest;

public interface EmailSenderService {
     String sendCreatedTodoMail();
     void sendReminderMail(String text);
     String deleteReminderEmail();

     void sendOTPVerificationMail(OTPVerificationRequest otpMailRequest);
     String registerEmail(OTPVerificationRequest createEmailRequest);


}
