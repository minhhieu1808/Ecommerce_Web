package com.example.backend.repository;

import com.example.backend.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUserByIdAndRole(int id, int isAdmin);
    UserEntity findUserByEmail(String email);
    UserEntity findUserById(int id);
}
