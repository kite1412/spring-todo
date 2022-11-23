package com.nrr.project.resourceserver.controller;

import com.nrr.project.resourceserver.dto.DeleteTodo;
import com.nrr.project.resourceserver.entity.Todo;
import com.nrr.project.resourceserver.service.contract.TodoService;
import com.nrr.project.resourceserver.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;

    @GetMapping("todolist")
    public List<String> showTodos(Authentication authentication) {
        userService.autoRegisterUser(authentication);
        return todoService.showTodos(authentication);
    }

    @PostMapping("todolist")
    public List<String> addTodo(Authentication authentication,
                                @RequestBody Todo todo) {
        userService.autoRegisterUser(authentication);
        return todoService.addTodo(authentication, todo);
    }

    @DeleteMapping("todolist")
    public List<String> deleteTodo(Authentication authentication,
                                   @RequestBody DeleteTodo number) {
        return todoService.deleteTodo(authentication, number);
    }
}
