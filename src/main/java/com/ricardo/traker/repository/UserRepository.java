package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByNickname(String nickname);
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
