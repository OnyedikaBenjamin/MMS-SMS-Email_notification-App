package com.benbillion.models.repository;

import com.benbillion.models.data.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByReminderEmail(String remainderMail);
}
