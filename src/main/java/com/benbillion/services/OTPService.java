package com.benbillion.services;

import com.benbillion.dtos.OTPVerificationRequest;

public interface OTPService {

     String verifyOTP(OTPVerificationRequest otpVerificationRequest);
     String resendOTP(OTPVerificationRequest otpVerificationRequest);
     String sendOTP(OTPVerificationRequest otpVerificationRequest);
     String deleteExpiredOTP();
}
