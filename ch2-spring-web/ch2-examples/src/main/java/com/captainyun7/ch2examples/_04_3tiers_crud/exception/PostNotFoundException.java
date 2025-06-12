package com.captainyun7.ch2examples._04_3tiers_crud.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("게시글을 찾을 수 없습니다. id=" + id);
    }
}