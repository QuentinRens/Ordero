package com.rensq.ordero.domain.order;

import com.rensq.ordero.domain.item.ItemGroup;

import java.util.List;

public class Order {
    private Integer id;
    private List<ItemGroup> itemGroups;


    private Order(){}

    public Integer getId() {
        return id;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItemGroups(List<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public static class OrderBuilder{
        private Integer id;
        private List<ItemGroup> itemGroups;

        private OrderBuilder(){}

        public static OrderBuilder order(){
            return new OrderBuilder();
        }

        public Order build(){
            Order order = new Order();
            order.setId(id);
            order.setItemGroups(itemGroups);
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
    }
}
