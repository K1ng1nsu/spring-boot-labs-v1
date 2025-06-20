package com.example.ch4labs.controller;

import com.example.ch4labs.dto.todo.TodoCreateRequest;
import com.example.ch4labs.dto.todo.TodoSearchRequest;
import com.example.ch4labs.dto.todo.TodoUpdateRequest;
import com.example.ch4labs.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoCreateRequest todoCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todoCreateRequest));
    }

    @GetMapping
    public ResponseEntity<?> getAllTodos(TodoSearchRequest todoSearchRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getAllTodo(todoSearchRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@RequestBody TodoUpdateRequest todoUpdateRequest, @PathVariable long id) {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(id, todoUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable long id) {
        todoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
