package com.rensq.ordero.domain.order;


import com.rensq.ordero.domain.item.ItemGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<ItemGroup> itemGroups;
    private UUID customerId;
    private BigDecimal price;

    private Order(){}

    public UUID getId() {
        return id;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setItemGroups(List<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class OrderBuilder{
        private UUID id;
        private List<ItemGroup> itemGroups;
        private UUID customerId;
        private BigDecimal price;


        private OrderBuilder(){}

        public static OrderBuilder order(){
            return new OrderBuilder();
        }

        public Order build(){
            Order order = new Order();
            order.setId(id);
            order.setItemGroups(itemGroups);
            order.setCustomerId(customerId);
            order.setPrice(price);
            return order;
        }

        public OrderBuilder withId(UUID id){
            this.id = id;
            return this;
        }

        public OrderBuilder withItemGroups(List <ItemGroup> itemGroups){
            this.itemGroups = itemGroups;
            return this;
        }

        public OrderBuilder withCustomerId (UUID customerId){
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder withPrice (BigDecimal price){
            this.price = price;
            return this;
        }
    }
}
