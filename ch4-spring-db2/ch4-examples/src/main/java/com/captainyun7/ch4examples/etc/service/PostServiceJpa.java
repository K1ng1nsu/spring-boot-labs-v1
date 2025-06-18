package com.captainyun7.ch4examples.etc.service;


import com.captainyun7.ch4examples.etc.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceJpa {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Post post) {
        em.persist(post);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }

    @Transactional
    public Post update(Long id, String title, String body) {
        Post post = em.find(Post.class, id);
        post.setTitle(title);
        post.setBody(body);
        return post;
    }

    @Transactional
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
