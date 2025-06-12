package com.captainyun7.ch2examples._04_3tiers_crud.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String body;
}