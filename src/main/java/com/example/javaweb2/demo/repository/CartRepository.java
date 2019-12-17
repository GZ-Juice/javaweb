package com.example.javaweb2.demo.repository;

import com.example.javaweb2.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByGoodsNameAndUserName(String goodsName, String userName);
    List<Cart> findByUserName(String name);
}
