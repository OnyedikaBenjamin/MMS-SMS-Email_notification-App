package com.benbillion.services;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class DeleteExpiredOTP {
   private OTPService otpService;

    public DeleteExpiredOTP(OTPService otpService) {
        this.otpService = otpService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void deleteExpiredOTP(){
       otpService.deleteExpiredOTP();
    }

}
