package com.lifelike.dev.springmongodbtodo.service;

import com.lifelike.dev.springmongodbtodo.exception.TodoCollectionException;
import com.lifelike.dev.springmongodbtodo.model.Todo;

import javax.validation.ConstraintViolationException;

public interface TodoService {

    void addTodoItem(Todo todo) throws ConstraintViolationException, TodoCollectionException;
}
