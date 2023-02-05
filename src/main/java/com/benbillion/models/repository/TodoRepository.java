package com.benbillion.models.repository;

import com.benbillion.models.data.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
