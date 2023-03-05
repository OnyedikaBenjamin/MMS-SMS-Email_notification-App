package com.benbillion.controller;

import com.benbillion.dtos.CreatedTodoMailRequest;
import com.benbillion.dtos.OTPVerificationRequest;
import com.benbillion.services.EmailSenderService;
import com.benbillion.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
@RestController
@RequestMapping(path = "api/v1/email")
public class EmailController {
    private  EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerEmail(@RequestBody OTPVerificationRequest otpVerificationRequest,
                                           HttpServletRequest httpServletRequest) {
        var email = emailSenderService.registerEmail(otpVerificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(email)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendCreatedTodoMail(HttpServletRequest httpServletRequest) {
         String email = emailSenderService.sendCreatedTodoMail();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(email)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmail(HttpServletRequest httpServletRequest) {
        var email = emailSenderService.deleteReminderEmail();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(email)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
