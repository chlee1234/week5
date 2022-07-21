package com.sparta.week05.service;

import com.sparta.week05.dto.FoodOrderDto;
import com.sparta.week05.dto.FoodOrderRequestDto;
import com.sparta.week05.dto.OrderDto;
import com.sparta.week05.dto.OrderRequestDto;
import com.sparta.week05.model.Food;
import com.sparta.week05.model.Order;
import com.sparta.week05.model.OrderInfo;
import com.sparta.week05.model.Restaurant;
import com.sparta.week05.repository.FoodRepository;
import com.sparta.week05.repository.OrderRepository;
import com.sparta.week05.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        FoodRepository foodRepository
    ){
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
    }

    @Transactional  // 주문 요청
    public OrderDto requestOrder(OrderRequestDto orderRequestDto){
        Long restaurantId = orderRequestDto.getRestaurantId();

        OrderDto orderDto = new OrderDto(); // 응답으로 보낼 주문 정보 선언

        Restaurant restaurant = restaurantRepository.findById(restaurantId) // 음식점 찾기
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));

        orderDto.setRestaurantName(restaurant.getName());
        orderDto.setDeliveryFee(restaurant.getDeliveryFee());


        List<FoodOrderRequestDto> FoodOrderList = orderRequestDto.getFoods(); // 요청받은 주문의 음식 리스트

        Order order = new Order();
        order.setRestaurant(restaurant);
        List<OrderInfo> orderInfoList = new ArrayList<>(); //Entity에 입력할 List
        List<FoodOrderDto> foodOrderDtoList = new ArrayList<>(); // 반환할  주문의 음식 리스트

        int totalPrice = 0;

        for (FoodOrderRequestDto foodOrderRequestDto : FoodOrderList) {
            Long foodId = foodOrderRequestDto.getId();

            Food food = foodRepository.findById(foodId) // 요청 받은 주문의 음식을 DB에 저장된 음식을 찾아 가져옴.
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식입니다."));
            int quantity = foodOrderRequestDto.getQuantity();

            if(quantity > 100 || quantity < 1){ // 주문 수 허용값: 1 ~ 100
                throw new IllegalArgumentException("주문 수량은 1 ~ 100개 입니다.");
            }

            int foodPrice = food.getPrice() * quantity; // 주문 음식 1개의 가격 * 주문 수량
            totalPrice += foodPrice;

            OrderInfo orderInfo = new OrderInfo(food, order, quantity); // 주문 정보를 넣어줌

            // 주문 정보를 주문 리스트에 저장
            orderInfoList.add(orderInfo);
            foodOrderDtoList.add(new FoodOrderDto(food.getName(), quantity, foodPrice));

        }
        order.setOrderInfos(orderInfoList);

        orderDto.setFoods(foodOrderDtoList);
        orderDto.setTotalPrice(totalPrice+ restaurant.getDeliveryFee()); //주문 음식 가격들의 총 합 + 배달비

        if(totalPrice < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("주문 금액이 최소 주문 가격보다 낮습니다.");
        }//"주문 음식 가격들의 총 합" 이 주문 음식점의 "최소주문 가격" 을 넘지 않을 시 에러 발생시킴

        orderRepository.save(order); // DB에 주문을 저장

        return orderDto;

    }

    @Transactional // 그 동안 성공한 모든 주문 요청을 조회
    public List<OrderDto> showOrders(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();


        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            orderDto.setRestaurantName(order.getRestaurant().getName());
            orderDto.setDeliveryFee(order.getRestaurant().getDeliveryFee());

            List<FoodOrderDto> foodOrderDtoList = new ArrayList<>();
            List<OrderInfo> orderInfoList = order.getOrderInfos();

            int totalPrice = 0;

            for (OrderInfo orderInfo : orderInfoList) {
                FoodOrderDto foodOrderDto = new FoodOrderDto(
                        orderInfo.getFood().getName(),
                        orderInfo.getQuantity(),
                        orderInfo.getFood().getPrice() * orderInfo.getQuantity());

                totalPrice += orderInfo.getFood().getPrice() * orderInfo.getQuantity();
                foodOrderDtoList.add(foodOrderDto);
            }
            orderDto.setFoods(foodOrderDtoList);
            orderDto.setTotalPrice(totalPrice+ orderDto.getDeliveryFee());

            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
