package com.benbillion.models.data;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
public class VerificationOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime usedAt;

    public VerificationOTP(String otp, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.otp = otp;
        this.createdAt=createdAt;
        this.expiredAt=expiredAt;
    }
}
