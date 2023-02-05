package com.benbillion.models.repository;

import com.benbillion.models.data.FinishedTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedTodoRepo extends JpaRepository<FinishedTodo,Long> {
}
