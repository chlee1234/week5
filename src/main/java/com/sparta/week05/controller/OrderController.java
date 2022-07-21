package com.sparta.week05.controller;

import com.sparta.week05.dto.OrderDto;
import com.sparta.week05.dto.OrderRequestDto;
import com.sparta.week05.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문
    @PostMapping("/order/request")
    public OrderDto requestOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.requestOrder(orderRequestDto);
    }
    // 주문 조회
    @GetMapping("/orders")
    public List<OrderDto> showOrders(){
        return orderService.showOrders();
    }
}
