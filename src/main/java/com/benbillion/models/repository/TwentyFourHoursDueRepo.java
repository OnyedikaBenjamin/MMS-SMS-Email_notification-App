package com.benbillion.models.repository;

import com.benbillion.models.data.TwentyFourHoursDueTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwentyFourHoursDueRepo extends JpaRepository<TwentyFourHoursDueTask, Long> {
}
