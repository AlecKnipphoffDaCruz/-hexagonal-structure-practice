package com.example.hexagonalstruct.order;

import com.example.hexagonalstruct.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderPersistencePort orderPersistencePort;
    @Autowired
    private UserService userService;

    public Order create(Long id){
        Order order = new Order(1L, userService.getUser(id), OrderStatus.ACTIVE, LocalDate.now());
        orderPersistencePort.save(order);
        return order;
    }

    public Order getByid(Long id){
        Optional<Order> optOrder = orderPersistencePort.getById(id);
            return optOrder.get();
    }

    public Order desactivate(Long id){
        Optional<Order> optionalOrder = orderPersistencePort.getById(id);
        Order oldOrder = optionalOrder.get();
        Order newOrder = new Order(oldOrder.id(), oldOrder.user(), OrderStatus.UNACTIVE, oldOrder.createdAt());
        orderPersistencePort.save(newOrder);
        return newOrder;
    }
    public Order activate(Long id){
        Optional<Order> optionalOrder = orderPersistencePort.getById(id);
        Order oldOrder = optionalOrder.get();
        Order newOrder = new Order(oldOrder.id(), oldOrder.user(), OrderStatus.ACTIVE, oldOrder.createdAt());
        orderPersistencePort.save(newOrder);
        return newOrder;
    }
}
