package com.example.ch4labs.dto;

import com.example.ch4labs.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
    private long id;
    private String title;
    private boolean completed;

    public static TodoResponse from(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(todo.getId());
        todoResponse.setTitle(todoResponse.getTitle());
        todoResponse.setCompleted(todoResponse.isCompleted());
        return todoResponse;
    }
}
