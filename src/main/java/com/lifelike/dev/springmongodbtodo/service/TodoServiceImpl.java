package com.lifelike.dev.springmongodbtodo.service;

import com.lifelike.dev.springmongodbtodo.exception.TodoCollectionException;
import com.lifelike.dev.springmongodbtodo.model.Todo;
import com.lifelike.dev.springmongodbtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public void addTodoItem(Todo todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<Todo> currentTodo = todoRepository.findByTodo(todo.getTodo());

        if (currentTodo.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.AlreadyExists());
        }

       todo.setCreatedAt(new Date(System.currentTimeMillis()));
       todoRepository.save(todo);
    }
}

