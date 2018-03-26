package com.rensq.ordero.domain.order;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class OrderRepository {
    private Map<UUID, Order> orders;

    public OrderRepository(){
        orders = new HashMap<>();
    }

    public Order getOrder (UUID id){
        return orders.get(id);
    }

    public List <Order> getOrderByCustomerId (UUID customerId){
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public Order storeOrder(Order order){
        order.setId(UUID.randomUUID());
        orders.put(order.getId(), order);
        return order;
    }
}
