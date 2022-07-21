package com.sparta.week05.controller;

import com.sparta.week05.dto.FoodDto;
import com.sparta.week05.model.Food;
import com.sparta.week05.model.Restaurant;
import com.sparta.week05.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }
    // 음식 등록
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerFood(@PathVariable Restaurant restaurantId, @RequestBody List<FoodDto> foodDto){
        foodService.registerFood(restaurantId, foodDto);
    }
    //메뉴판 조회
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getFoods(@PathVariable Restaurant restaurantId) {
        return foodService.getFoods(restaurantId);
    }
}
