package com.lifelike.dev.springmongodbtodo.controller;

import com.lifelike.dev.springmongodbtodo.exception.TodoCollectionException;
import com.lifelike.dev.springmongodbtodo.model.Todo;
import com.lifelike.dev.springmongodbtodo.service.TodoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/")
public class TodoController {
    private final TodoServiceImpl todoService;

    @GetMapping("todos")
    public ResponseEntity<?> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") String id) {
        try {
            Todo todo = todoService.getTodo(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("todos")
    public ResponseEntity<?> addTodo(@RequestBody Todo todo) {
        try {
            todoService.addTodoItem(todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id, @RequestBody Todo todo) {
        try {
            todoService.updateTodo(id, todo);
            return new ResponseEntity<>("Updated Todo successfully", HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Deleted Todo successfully", HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
