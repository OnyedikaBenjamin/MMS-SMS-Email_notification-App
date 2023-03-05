package com.benbillion.models.repository;

import com.benbillion.models.data.ImminentTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImminentTodoRepo extends JpaRepository<ImminentTask, Long> {
}
