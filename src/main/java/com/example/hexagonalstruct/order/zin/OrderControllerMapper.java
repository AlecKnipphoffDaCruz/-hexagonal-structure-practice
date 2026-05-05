package com.example.hexagonalstruct.order.zin;

import com.example.hexagonalstruct.order.Order;
import com.example.hexagonalstruct.order.zdto.OrderRequest;
import com.example.hexagonalstruct.order.zdto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderControllerMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.id(), order.user().name(), order.orderStatus(), order.createdAt());
    }
}
