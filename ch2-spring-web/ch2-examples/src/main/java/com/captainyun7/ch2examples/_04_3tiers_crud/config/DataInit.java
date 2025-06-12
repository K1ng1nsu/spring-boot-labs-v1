package com.captainyun7.ch2examples._04_3tiers_crud.config;


import com.captainyun7.ch2examples._04_3tiers_crud.domain.Post;
import com.captainyun7.ch2examples._04_3tiers_crud.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInit {

    private final PostRepository repository;

    public DataInit(PostRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        repository.save(new Post(null, "리액트 질문좀요", "리액트에서 커스텀 훅이 꼭 필요한가요? 왜 쓰는거에요?"));
        repository.save(new Post(null, "Next.js는 왜 인기가 이렇게 많아요?", "요즘 주변 개발자들 Next.js 많이 쓰던데 왜 이렇게 인기가 많은지 궁금하네요..."));
        repository.save(new Post(null, "Devtools 설정 방법좀요", "알려주세요!!!"));
    }
}
