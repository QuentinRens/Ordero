package com.rensq.ordero.domain.order;

import com.rensq.ordero.domain.item.ItemGroup;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class OrderRepository {
    private Map<UUID, Order> orders;

    public OrderRepository() {
        orders = new HashMap<>();
    }

    public void clear(){
        orders.clear();
    }

    public Order getOrder(UUID id) {
        return orders.get(id);
    }

    public List<Order> getOrderByCustomerId(UUID customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public Map<Order, List<ItemGroup>> getOrderWithItemGroupsToBeShippedToday() {
        return orders.values().stream()
                .map(order -> order.getItemGroups())
                .flatMap(list -> list.stream())
                .filter(itemGroup -> itemGroup.getShippingDate().isEqual(LocalDate.now()))
                .collect(Collectors.groupingBy(o -> getOrder(o.getOrderId())));
    }

    public List<Order> getAllOrders(){
        return new ArrayList<>(orders.values())  ;  }

    public Order storeOrder(Order order) {
        UUID orderId = UUID.randomUUID();
        order.setId(orderId);
        for (ItemGroup itemGroup : order.getItemGroups()) {
            itemGroup.setOrderId(orderId);
        }
        orders.put(order.getId(), order);
        return order;
    }
}
