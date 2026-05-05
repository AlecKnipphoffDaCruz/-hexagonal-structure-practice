package com.example.hexagonalstruct.order.zout;

import com.example.hexagonalstruct.order.OrderStatus;
import com.example.hexagonalstruct.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//anotations from jpa
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    Long id;
    User user;
    OrderStatus orderStatus;
    LocalDate createdAt;
}

