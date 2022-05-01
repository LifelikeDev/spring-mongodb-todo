package com.lifelike.dev.springmongodbtodo.service;

import com.lifelike.dev.springmongodbtodo.exception.TodoCollectionException;
import com.lifelike.dev.springmongodbtodo.model.Todo;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {

    List<Todo> getAllTodos();

    Todo getTodo(String id) throws TodoCollectionException;

    void addTodoItem(Todo todo) throws ConstraintViolationException, TodoCollectionException;

    void updateTodo(String id, Todo todo) throws TodoCollectionException;
}
