package com.sparta.week05.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class FoodOrderDto {

    String name;
    int quantity;
    int price; //음식가격 * quantity
    public FoodOrderDto(String name, int quantity, int price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    }
}