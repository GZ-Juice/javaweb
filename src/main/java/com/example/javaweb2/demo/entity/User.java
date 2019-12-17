package com.example.javaweb2.demo.entity;

import javax.persistence.*;

/**
 * 用户表
 */
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 用户姓名
    @Column
    private String name;
    // 用户邮件地址
    @Column
    private String email;
    // 用户密码
    @Column
    private String password;

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
