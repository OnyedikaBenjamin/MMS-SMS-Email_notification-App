package com.benbillion.models.repository;

import com.benbillion.models.data.OneHourDueTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneHourDueRepo extends JpaRepository<OneHourDueTask, Long> {
}
