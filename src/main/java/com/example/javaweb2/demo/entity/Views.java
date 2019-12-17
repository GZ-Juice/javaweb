package com.example.javaweb2.demo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 浏览日志表
 */
@Entity
@Table
public class Views {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 用户姓名
    @Column
    private String userName;
    // 商品名称
    @Column
    private String goodsName;
    // 浏览时间
    @Column
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
