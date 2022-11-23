package com.nrr.project.resourceserver.repository;

import com.nrr.project.resourceserver.entity.Todo;
import com.nrr.project.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUserId(User userid);
}
