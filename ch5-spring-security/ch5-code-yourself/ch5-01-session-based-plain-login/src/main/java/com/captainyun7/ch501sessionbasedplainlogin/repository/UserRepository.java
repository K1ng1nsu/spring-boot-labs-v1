package com.captainyun7.ch501sessionbasedplainlogin.repository;

import com.captainyun7.ch501sessionbasedplainlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);     // TODO

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
}