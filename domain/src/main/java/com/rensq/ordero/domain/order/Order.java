package com.rensq.ordero.domain.order;


import com.rensq.ordero.domain.item.ItemGroup;

import java.util.List;
import java.util.UUID;

public class Order {
    private Integer id;
    private List<ItemGroup> itemGroups;
    private UUID customerId;

    private Order(){}

    public Integer getId() {
        return id;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItemGroups(List<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public static class OrderBuilder{
        private Integer id;
        private List<ItemGroup> itemGroups;
        private UUID customerId;


        private OrderBuilder(){}

        public static OrderBuilder order(){
            return new OrderBuilder();
        }

        public Order build(){
            Order order = new Order();
            order.setId(id);
            order.setItemGroups(itemGroups);
            order.setCustomerId(customerId);
            return order;
        }

        public OrderBuilder withId(Integer id){
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
    }
}
