package com.captainyun7.ch2examples._04_3tiers_crud.repository;

import com.captainyun7.ch2examples._04_3tiers_crud.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private long sequence = 0L;

    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long id) {
        store.remove(id);
    }
}

