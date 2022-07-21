package com.sparta.week05.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID") //id값을 가지는 쪽이 주인
    private Order order;

    private int quantity;

    protected OrderInfo() {
    }

    public OrderInfo(Food food, Order order, int quantity) {
        this.food = food;
        this.order = order;
        this.quantity = quantity;
    }
}
