package com.lifelike.dev.springmongodbtodo.service;

import com.lifelike.dev.springmongodbtodo.exception.TodoCollectionException;
import com.lifelike.dev.springmongodbtodo.model.Todo;
import com.lifelike.dev.springmongodbtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<Todo>();
        }
    }

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

