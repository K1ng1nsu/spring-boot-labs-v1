package com.captainyun7.ch4examples.etc.service;

import com.captainyun7.ch4examples.etc.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaBasicConcepts {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void findTwice(Long id) {
        Post post1 = em.find(Post.class, id);
        Post post2 = em.find(Post.class, id);
        System.out.println(post1 == post2); // true? 확인
    }

    @Transactional
    public void updateWithoutFlush(Long id) {
        Post post = em.find(Post.class, id);
        post.setTitle("변경됨");

        // flush 호출 안 해도 커밋 시점에 update 되는지 로그로 확인
    }

    @Transactional
    public void flushThenFind(Long id) {
        Post post = em.find(Post.class, id);
        post.setTitle("변경됨");

        em.flush(); // UPDATE 쿼리 바로 실행
        em.clear(); // 1차 캐시 제거

        Post newPost = em.find(Post.class, id); // DB에서 다시 조회됨
        System.out.println(post == newPost); // false
    }

    @Transactional
    public void testDetach(Long id) {
        Post post = em.find(Post.class, id);
        em.detach(post);

        post.setTitle("변경무시됨"); // 반영 안 됨
    }

    @Transactional
    public void testMerge() {
        Post detached = new Post(1L, "병합 제목", "병합 본문");
        Post merged = em.merge(detached);
        System.out.println(detached == merged); // false
    }


    @Transactional
    public void testBatchInsert() {
        for (int i = 1; i <= 5; i++) {
            Post p = new Post(null, "제목" + i, "내용" + i);
            em.persist(p); // SQL 안 나감
        }
        // flush(); // 수동 실행해보기

        System.out.println("SQL 나갔는지 로그 확인");
    }
}
