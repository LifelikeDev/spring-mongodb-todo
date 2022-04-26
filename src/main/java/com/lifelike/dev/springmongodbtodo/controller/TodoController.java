package com.lifelike.dev.springmongodbtodo.controller;

import com.lifelike.dev.springmongodbtodo.model.Todo;
import com.lifelike.dev.springmongodbtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/")
public class TodoController {
    private final TodoRepository todoRepository;

    @GetMapping("todos")
    public ResponseEntity<?> getAllTodos() {
        List<Todo> todoList = todoRepository.findAll();

        if(todoList.size() > 0) {
            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos found. Consider creating one.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") String id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if(todo.isPresent()) {
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("todos")
    public ResponseEntity<?> addTodo(@RequestBody Todo todo) {
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id, @RequestBody Todo todo) {

        Optional<Todo> currentToDo = todoRepository.findById(id);

        if (currentToDo.isPresent()) {
            Todo todoToBeUpdated = currentToDo.get();

            todoToBeUpdated.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToBeUpdated.getTodo());
            todoToBeUpdated.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToBeUpdated.getDescription());
            todoToBeUpdated.setIsCompleted(todo.getIsCompleted() != null ? todo.getIsCompleted() : todoToBeUpdated.getIsCompleted());
            todoToBeUpdated.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoToBeUpdated);
            return new ResponseEntity<>(todoToBeUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
