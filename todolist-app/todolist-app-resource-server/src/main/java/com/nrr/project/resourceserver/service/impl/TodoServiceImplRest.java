package com.nrr.project.resourceserver.service.impl;

import com.nrr.project.resourceserver.dto.DeleteTodo;
import com.nrr.project.resourceserver.entity.Todo;
import com.nrr.project.resourceserver.entity.User;
import com.nrr.project.resourceserver.repository.TodoRepository;
import com.nrr.project.resourceserver.repository.UserRepository;
import com.nrr.project.resourceserver.service.contract.TodoService;
import com.nrr.project.resourceserver.util.ThrowUsernameNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class TodoServiceImplRest implements TodoService<String> {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public List<String> showTodos(Authentication authentication) {
        String userId = authentication.getName();
        User user = userRepository.findById(userId)
                .orElseThrow(ThrowUsernameNotFound::get);
        List<Todo> searchList = todoRepository.findAllByUserId(user);
        List<String> todolist = searchList.stream()
                .map(Todo::getTodo)
                .toList();
        List<String> converted = new ArrayList<>();
        int counter = 0;
        for (String todo : todolist) {
            counter++;
            converted.add(counter + ". " + todo);
        }
        return converted;
    }

    @Override
    public List<String> addTodo(Authentication authentication, Todo todo) {
        String username = authentication.getName();
        User userId = userRepository.findById(username)
                .orElseThrow(ThrowUsernameNotFound::get);
        todo.setUserId(userId);
        todoRepository.save(todo);
        return showTodos(authentication);
    }

    @Override
    public List<String> deleteTodo(Authentication authentication,
                                   DeleteTodo delete) {
        String username = authentication.getName();
        User user = userRepository.findById(username)
                .orElseThrow(ThrowUsernameNotFound::get);
        List<Todo> todos = todoRepository.findAllByUserId(user);
        int i = delete.number() - 1;
        todos.remove(i);
        List<Todo> updated = new ArrayList<>(todos);
        todoRepository.deleteAll(todoRepository.findAllByUserId(user));
        todoRepository.saveAll(updated);
        return showTodos(authentication);
    }
}
