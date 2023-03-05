package com.benbillion.controller;

import com.benbillion.dtos.OTPVerificationRequest;
import com.benbillion.services.OTPService;
import com.benbillion.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "api/v1/otp")
public class OtpVerificationController {
    private final OTPService otpService;

    public OtpVerificationController(OTPService otpService){
        this.otpService=otpService;
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendOTP(@RequestBody OTPVerificationRequest otpVerificationRequest,
                                     HttpServletRequest httpServletRequest) {
        String otp = otpService.sendOTP(otpVerificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(otp)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOTP(@RequestBody OTPVerificationRequest otpVerificationRequest,
                                     HttpServletRequest httpServletRequest) {
        String otp = otpService.verifyOTP(otpVerificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(otp)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendOTP(@RequestBody OTPVerificationRequest otpVerificationRequest,
                                       HttpServletRequest httpServletRequest) {
        String otp = otpService.verifyOTP(otpVerificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(otp)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
