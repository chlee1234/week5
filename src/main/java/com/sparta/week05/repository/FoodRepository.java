package com.sparta.week05.repository;

import com.sparta.week05.model.Food;
import com.sparta.week05.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByRestaurant(Restaurant restaurantId);
}