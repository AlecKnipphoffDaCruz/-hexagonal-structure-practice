package com.example.hexagonalstruct.order;

import java.util.Optional;

public interface OrderPersistencePort {
    Order save(Order order);

    Optional<Order> getById(Long id);
}
