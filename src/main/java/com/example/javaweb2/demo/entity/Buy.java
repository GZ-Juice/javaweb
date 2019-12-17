package com.example.javaweb2.demo.entity;

import javax.persistence.*;

/**
 *  订单表
 */
@Entity
@Table
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 用户名字
    @Column
    private String userName;
    // 商品名字
    @Column
    private String goodsName;
    // 商品单价
    @Column
    private Integer price;
    // 商品数量
    @Column
    private Integer num;
    // 商品总价
    @Column
    private Integer total;
    // 商品状态
    @Column
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
