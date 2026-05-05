package com.example.hexagonalstruct.order;

import com.example.hexagonalstruct.user.User;

import java.time.LocalDate;

public record Order(
        Long id,
        User user,
        OrderStatus orderStatus,
        LocalDate createdAt
) {
}
