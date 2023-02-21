package com.benbillion.services;

import com.benbillion.dtos.CreatedTodoMailRequest;
import com.benbillion.dtos.OTPVerificationRequest;

public interface EmailSenderService {

     String deleteReminderEmail();
     String sendCreatedTodoMail();
     String sendOTPVerificationMail(OTPVerificationRequest otpMailRequest);
     String registerEmail(OTPVerificationRequest createEmailRequest);
}
