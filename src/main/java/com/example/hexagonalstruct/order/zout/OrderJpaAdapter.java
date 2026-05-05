package com.example.hexagonalstruct.order.zout;

import com.example.hexagonalstruct.order.Order;
import com.example.hexagonalstruct.order.OrderPersistencePort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderJpaAdapter implements OrderPersistencePort {

    private final Map<Long, OrderEntity> database = new HashMap<>();
    private final OrderMapper orderMapper;

    public OrderJpaAdapter(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order){
        OrderEntity orderEntity = orderMapper.toEntity(order);
        database.put(orderEntity.id, orderEntity);
        return orderMapper.toDomain(orderEntity);
    }

    @Override
    public Optional<Order> getById(Long id){
        return Optional.ofNullable(database.get(id))
                .map(orderMapper::toDomain);
    }
}
