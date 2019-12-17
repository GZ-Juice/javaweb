package com.example.javaweb2.demo.repository;


import com.example.javaweb2.demo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    List<Goods> findByName(String name);
}
