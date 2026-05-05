package com.example.hexagonalstruct.order.zdto;

import com.example.hexagonalstruct.order.OrderStatus;

import java.time.LocalDate;

public record OrderResponse(
        Long id,
        String userName,
        OrderStatus status,
        LocalDate createdAt
) {
}
