package com.example.javaweb2.demo.repository;

import com.example.javaweb2.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByName(String name);
}