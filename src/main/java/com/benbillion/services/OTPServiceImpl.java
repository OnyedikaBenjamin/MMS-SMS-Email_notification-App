package com.benbillion.services;

import com.benbillion.dtos.OTPVerificationRequest;
import com.benbillion.models.data.Email;
import com.benbillion.models.data.VerificationOTP;
import com.benbillion.models.repository.EmailRepository;
import com.benbillion.models.repository.OTPRepository;
import exceptions.GenericHandlerException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService {
    private final EmailSenderService emailSenderService;
    private final EmailRepository emailRepository;
    private final OTPRepository otpRepository;

    public OTPServiceImpl(EmailSenderService emailSenderService,
                          EmailRepository emailRepository,
                          OTPRepository otpRepository) {
        this.emailSenderService = emailSenderService;
        this.emailRepository=emailRepository;
        this.otpRepository=otpRepository;
    }

    public static StringBuilder generateOTP(){
        String num = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(num.charAt(random.nextInt(10)));}
        return stringBuilder;
    }
    private String getString(OTPVerificationRequest otpVerificationRequest) {
        String generatedOtp = generateOTP().toString();
        VerificationOTP verificationOTP = new VerificationOTP(generatedOtp,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));
        otpRepository.save(verificationOTP);
        otpVerificationRequest.setOtp(generatedOtp);
        otpVerificationRequest.setBody("Your One time password is "+generatedOtp
                +" This password expires after one minute!!!");
        emailSenderService.sendOTPVerificationMail(otpVerificationRequest);
        return generatedOtp;
    }

    @Override
    public String sendOTP(OTPVerificationRequest otpVerificationRequest){
        return getString(otpVerificationRequest);
    }
    @Override
    public String resendOTP(OTPVerificationRequest otpVerificationRequest){
        otpRepository.deleteAll();
        return getString(otpVerificationRequest);
    }

    @Override
    public String verifyOTP(OTPVerificationRequest otpVerificationRequest) {
        VerificationOTP queriedVerificationOTp = otpRepository.findByOtp(otpVerificationRequest.getOtp())
                .orElseThrow(()->  new GenericHandlerException("Invalid VerificationOTP"));
        if(queriedVerificationOTp.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new GenericHandlerException("Token has been expired");
        }
        if(queriedVerificationOTp.getUsedAt()!=null){
            throw new GenericHandlerException("Otp has already been used");
        }
        queriedVerificationOTp.setUsedAt(LocalDateTime.now());
        otpRepository.save(queriedVerificationOTp);
        Email newEmail = new Email(otpVerificationRequest.getReminderEmail());
        emailRepository.deleteAll();      // user can only have one emailAdd!
        emailRepository.save(newEmail);  // if verified, save!
        return "Verification Successful";
    }

    public String deleteExpiredOTP(){
        otpRepository.deleteVerificationOTPByExpiredAtBefore(LocalDateTime.now());
        return "";
    }

}
