package com.example.ch4labs.dto.todo;

import com.example.ch4labs.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoUpdateRequest {
    private String title;
    private Boolean completed;

    public Todo toDomain() {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(completed);
        return todo;
    }
}
