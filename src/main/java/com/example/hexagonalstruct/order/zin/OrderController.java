package com.example.hexagonalstruct.order.zin;

import com.example.hexagonalstruct.order.Order;
import com.example.hexagonalstruct.order.OrderService;
import com.example.hexagonalstruct.order.zdto.OrderRequest;
import com.example.hexagonalstruct.order.zdto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final OrderControllerMapper orderControllerMapper;

    public OrderController(OrderService orderService, OrderControllerMapper orderControllerMapper) {
        this.orderService = orderService;
        this.orderControllerMapper = orderControllerMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        Order order = orderService.create(request.userId());
        return ResponseEntity.ok(orderControllerMapper.toResponse(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        Order order = orderService.getByid(id);
        return ResponseEntity.ok(orderControllerMapper.toResponse(order));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<OrderResponse> activate(@PathVariable Long id) {
        Order order = orderService.activate(id);
        return ResponseEntity.ok(orderControllerMapper.toResponse(order));
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<OrderResponse> desactivate(@PathVariable Long id) {
        Order order = orderService.desactivate(id);
        return ResponseEntity.ok(orderControllerMapper.toResponse(order));
    }
}
