package com.sparta.week05.service;

import com.sparta.week05.dto.FoodDto;
import com.sparta.week05.model.Food;
import com.sparta.week05.model.Restaurant;
import com.sparta.week05.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    //음식 등록
    public void registerFood(Restaurant restarantId, List<FoodDto> foodDto) {

        // 중복 음식 유효성 검증
        for (FoodDto dto : foodDto) {  // 같은 음식점 내에서는 음식 이름이 중복될 수 없음
            List<Food> foods = foodRepository.findAllByRestaurant(restarantId);
            for (Food food : foods) {  // 입력 받은 음식을 DB에 저장되어 있는 음식과 for문으로 비교
                String name = dto.getName();
                if (food.getName().equals(name)) {
                    throw new IllegalArgumentException("이미 존재하는 음식입니다.");
                }
            }
            // 음식 가격 유효성 검증
            int price = dto.getPrice();
            if (price < 100 || price > 1000000) {  // 허용값: 100원 ~ 1,000,000원
                throw new IllegalArgumentException("음식 가격은 100 ~ 1,000,000원 입니다.");
            }
            if (price % 100 != 0) {  // 100 원 단위로만 입력 가능
                throw new IllegalArgumentException("음식 가격은 100원 단위로만 입력가능합니다.");
            }
            Food foodSave = new Food(restarantId, dto);
            foodRepository.save(foodSave);
        }
    }

    // 메뉴판 조회
    @Transactional  // 하나의 음식점에 등록된 모든 음식 정보 조회 ( restaurantId로 find)
    public List<Food> getFoods(Restaurant restaurantId) {
        return foodRepository.findAllByRestaurant(restaurantId);
    }
}
