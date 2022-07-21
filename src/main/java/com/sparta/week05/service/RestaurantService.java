package com.sparta.week05.service;

import com.sparta.week05.dto.RestaurantDto;
import com.sparta.week05.model.Restaurant;
import com.sparta.week05.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // 음식점 정보 입력받아 등록
    public Restaurant registerRestaurant(RestaurantDto restaurantDto){
        int minOrderPrice = restaurantDto.getMinOrderPrice();
        int deliveryFee = restaurantDto.getDeliveryFee();
        // 최소 주문 가격 유효성 검증
        if (minOrderPrice < 1000 || minOrderPrice > 100000){ // 허용값: 1,000원 ~ 100,000원 입력
            throw new IllegalArgumentException("최소 주문 가격은 1,000 ~ 100,000원 입니다.");
        }
        if (minOrderPrice%100 != 0){ // 100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원은 입력 가능)
            throw new IllegalArgumentException("최소 주문 가격은 100원 단위로만 입력가능합니다.");
        }
        // 기본 배달비 유효성 검증
        if (deliveryFee < 0 || deliveryFee > 10000){ // 허용값: 0원 ~ 10,000원
            throw new IllegalArgumentException("기본 배달비는 0 ~ 10,000원 입니다.");
        }
        if (deliveryFee%500 != 0){  // 500 원 단위로만 입력 가능
            throw new IllegalArgumentException("기본 배달비는 500원 단위로만 입력가능합니다.");
        }
        Restaurant restaurant = new Restaurant(restaurantDto);
        return restaurantRepository.save(restaurant);

    }
    // 등록된 모든 음식점 정보 조회
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }
}
