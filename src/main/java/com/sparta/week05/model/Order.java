package com.sparta.week05.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "orders") // SQL order --> 순서를 나열하는 용어 'order by' 그래서 테이블 내임을 orders로 바꿔줌.
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne // 주문 : 음식점은 다대일 관계
    @JoinColumn(name = "RESTAURANT_ID")  // 음식점 ID를 FK로 사용
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 주문 상세 정보와 연관관계 매핑
    private List<OrderInfo> orderInfos = new ArrayList<>();


}
