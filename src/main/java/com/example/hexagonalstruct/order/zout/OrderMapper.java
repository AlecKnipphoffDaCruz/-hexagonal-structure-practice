package com.example.hexagonalstruct.order.zout;

import com.example.hexagonalstruct.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toDomain(OrderEntity order){
        return new Order(order.getId(), order.getUser(), order.getOrderStatus(), order.getCreatedAt());
    }
    public OrderEntity toEntity(Order order){
        return new OrderEntity(order.id(), order.user(), order.orderStatus(), order.createdAt());
    }
}
