package com.sparta.week05.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantDto {
    private String name;
    private int minOrderPrice;
    private int deliveryFee;

    }


