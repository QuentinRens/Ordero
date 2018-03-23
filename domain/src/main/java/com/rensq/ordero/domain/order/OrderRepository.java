package com.rensq.ordero.domain.order;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class OrderRepository {
    private Map<Integer, Order> orders;
    private static int databaseIndex = 0;

    public OrderRepository(){
        orders = new HashMap<>();
    }

    public Order getOrder (int id){
        return orders.get(id);
    }

    public Order storeOrder(Order order){
        order.setId(++databaseIndex);
        orders.put(order.getId(), order);
        return order;
    }
}
