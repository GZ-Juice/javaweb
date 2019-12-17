package com.example.javaweb2.demo.repository;

import com.example.javaweb2.demo.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Integer> {
}
