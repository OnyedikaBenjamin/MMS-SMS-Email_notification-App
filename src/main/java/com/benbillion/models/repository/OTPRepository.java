package com.benbillion.models.repository;

import com.benbillion.models.data.VerificationOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<VerificationOTP, Long> {
    Optional<VerificationOTP> findByOtp(String otp);
}
