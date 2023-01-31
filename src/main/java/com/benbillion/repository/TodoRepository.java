package com.benbillion.repository;

import com.benbillion.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TodoReopsitory extends JpaRepository<Todo, Long> {
}
