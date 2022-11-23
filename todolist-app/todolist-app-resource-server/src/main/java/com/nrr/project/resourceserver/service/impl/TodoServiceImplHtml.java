package com.nrr.project.resourceserver.service.impl;

import com.nrr.project.resourceserver.dto.AddTodo;
import com.nrr.project.resourceserver.dto.DeleteTodo;
import com.nrr.project.resourceserver.entity.Todo;
import com.nrr.project.resourceserver.entity.User;
import com.nrr.project.resourceserver.repository.TodoRepository;
import com.nrr.project.resourceserver.repository.UserRepository;
import com.nrr.project.resourceserver.service.contract.TodoService;
import com.nrr.project.resourceserver.util.ThrowUsernameNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TodoServiceImplHtml implements TodoService<Todo> {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Override
    public List<Todo> showTodos(Authentication authentication) {
        String userId = authentication.getName();
        User user = userRepository.findById(userId)
                .orElseThrow(ThrowUsernameNotFound::get);
        return todoRepository.findAllByUserId(user);
    }

    @Override
    public List<Todo> addTodo(Authentication authentication, Todo todo) {
        return null;
    }

    @Override
    public List<Todo> addTodoForHtml(Authentication authentication, String todo) {
        String username = authentication.getName();
        User userId = userRepository.findById(username)
                .orElseThrow(ThrowUsernameNotFound::get);
        Todo newTodo = Todo.builder()
                .todo(todo)
                .userId(userId)
                .build();
        todoRepository.save(newTodo);
        log.info("todo: '{}' added to user: '{}'", todo, username);
        return showTodos(authentication);
    }

    @Override
    public List<Todo> deleteTodo(Authentication authentication, DeleteTodo delete) {
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
