package com.nrr.project.resourceserver.controller;

import com.nrr.project.resourceserver.entity.Todo;
import com.nrr.project.resourceserver.service.contract.TodoService;
import com.nrr.project.resourceserver.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class TodoControllerHtml {

    private final UserService userService;
    private final TodoService<Todo> todoService;

    /**
     * IMPORTANT NOTE:
     * Register the object to thymeleaf with injecting the object
     * that want to be registered into method parameter.
     * The default registered object name in thymeleaf would be
     * the name of the object with camel case e.g(user).
     * Use @ModelAttribute annotation to change the registered object name.
     * Injected object name in parameter doesn't affect anything.
     */
    @GetMapping("todolist")
    public String showTodos(Authentication authentication,
                            Model model,
                            Todo todo) {
        userService.autoRegisterUser(authentication);
        List<Todo> todolist = todoService.showTodos(authentication);
        model.addAttribute("todolist", todolist);
        return "show-todolist";
    }

    /**
     * IMPORTANT NOTE:
     * The object name in the method parameter represent the model's field,
     * otherwise the mapping between thymeleaf won't occur.
     * e.g:
     * public class User{
     *
     *     private String username;
     * }
     * The object name in the parameter should be 'username'.
     */
    @PostMapping("add")
    public String addTodo(Authentication authentication,
                          String todo) {
        userService.autoRegisterUser(authentication);
        todoService.addTodoForHtml(authentication, todo);
        return "redirect:/todolist";
    }
}
