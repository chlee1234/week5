package com.sparta.week05.model;

import com.sparta.week05.dto.FoodDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Food {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne // 음식 : 음식점은 다대일 관계
    @JoinColumn(name = "RESTAURANT_ID", nullable = false) // 음식점 ID를 FK로 사용
    private Restaurant restaurant;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false일때 중복 허용)
    @Column(nullable = false)
    private String name;  // 음식명

    @Column(nullable = false)
    private int price;  // 가격

    public Food(Restaurant restaurantId, FoodDto foodDto){
        this.name = foodDto.getName();
        this.price = foodDto.getPrice();
        this.restaurant = restaurantId;

    }
}
