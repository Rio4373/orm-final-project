package com.example.ormfinalproject.service;

import com.example.ormfinalproject.model.User;
import com.example.ormfinalproject.model.UserRole;

import java.util.List;

public interface UserService {

    User createUser(String name, String email, UserRole role);

    User getUser(Long id);

    List<User> getAllUsers();
}


