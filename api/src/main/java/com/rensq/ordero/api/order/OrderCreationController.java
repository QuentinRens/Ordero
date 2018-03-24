package com.rensq.ordero.api.order;


import com.rensq.ordero.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping (path = "/order_creation")
public class OrderCreationController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    @Inject
    public OrderCreationController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping (consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.CREATED)
    public OrderDto makeOrder(@RequestBody OrderDto orderDto){
        return orderMapper.toDto(orderService.createOrder(orderMapper.toDomain(orderDto)));
    }
}
