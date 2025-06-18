package com.example.ch4labs.service;

import com.example.ch4labs.domain.Todo;
import com.example.ch4labs.dto.TodoCreateRequest;
import com.example.ch4labs.dto.TodoResponse;
import com.example.ch4labs.dto.TodoSearchRequest;
import com.example.ch4labs.dto.TodoUpdateRequest;
import com.example.ch4labs.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponse createTodo(TodoCreateRequest todoCreateRequest) {
        Todo save = todoRepository.save(todoCreateRequest.toDomain());
        return TodoResponse.from(save);
    }


    public List<TodoResponse> getAllTodo(TodoSearchRequest todoSearchRequest) {
        List<Todo> all;

        if (todoSearchRequest.getKeyword() == null) {
            all = todoRepository.findAll();
        } else {
            all = todoRepository.findByTitleContains(todoSearchRequest.getKeyword());
        }

        return all.stream().map(TodoResponse::from).collect(Collectors.toList());
    }

    public TodoResponse updateTodo(long id, TodoUpdateRequest todoUpdateRequest) {
        Todo todoById = getTodoById(id);
        todoById.setTitle(todoUpdateRequest.getTitle());
        todoById.setCompleted(todoUpdateRequest.getCompleted());
        todoRepository.save(todoById);
        return TodoResponse.from(todoById);
    }

    public void delete(long id) {
        todoRepository.deleteById(id);
    }

    private Todo getTodoById(long id) {
        return todoRepository.findById(id).orElse(null);
    }
}
