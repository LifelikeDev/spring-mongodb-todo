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
            return new ArrayList<>();
        }
    }

    @Override
    public Todo getTodo(String id) throws TodoCollectionException {
        Optional<Todo> currentTodo = todoRepository.findById(id);

        if (currentTodo.isEmpty()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }

        return currentTodo.get();
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

    @Override
    public void updateTodo(String id, Todo todoParam) throws TodoCollectionException {
        Optional<Todo> foundTodo = todoRepository.findById(id);
        Optional<Todo> foundTodoWithContent = todoRepository.findByTodo(todoParam.getTodo());

        if (foundTodo.isPresent()) {
            if (foundTodoWithContent.isPresent() && foundTodoWithContent.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.AlreadyExists());
            }

            Todo todoToBeUpdated = foundTodo.get();

            todoToBeUpdated.setTodo(todoParam.getTodo());
            todoToBeUpdated.setDescription(todoParam.getDescription());
            todoToBeUpdated.setIsCompleted(todoParam.getIsCompleted());
            todoToBeUpdated.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoToBeUpdated);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        Optional<Todo> todoToBeDeleted = todoRepository.findById(id);

        if (todoToBeDeleted.isEmpty()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }

        todoRepository.delete(todoToBeDeleted.get());
    }
}

