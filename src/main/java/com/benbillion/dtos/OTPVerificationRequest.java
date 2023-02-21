package com.benbillion.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class OTPVerificationRequest {
    private String reminderEmail;
    private String otp;
    private String body;

    public OTPVerificationRequest(String otp, String reminderEmail){
        this.otp=otp;
        this.reminderEmail=reminderEmail;
    }
}
