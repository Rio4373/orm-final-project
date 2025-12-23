package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.User;
import com.example.ormfinalproject.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);
}


