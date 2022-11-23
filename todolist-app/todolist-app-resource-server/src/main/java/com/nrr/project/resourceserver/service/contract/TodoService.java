package com.nrr.project.resourceserver.service.contract;

import com.nrr.project.resourceserver.dto.DeleteTodo;
import com.nrr.project.resourceserver.entity.Todo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TodoService<T> {

    List<T> showTodos(Authentication authentication);

    List<T> addTodo(Authentication authentication, Todo todo);

    List<T> deleteTodo(Authentication authentication, DeleteTodo delete);

    default List<T> addTodoForHtml(Authentication authentication, String todo) {
        return null;
    }
}
