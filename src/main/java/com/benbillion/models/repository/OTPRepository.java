package com.benbillion.models.repository;

import com.benbillion.models.data.VerificationOTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public interface OTPRepository extends JpaRepository<VerificationOTP, Long> {
    Optional<VerificationOTP> findByOtp(String otp);
    void deleteVerificationOTPByExpiredAtBefore(LocalDateTime now);
}
