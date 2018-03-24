package com.rensq.ordero.api.order;


import com.rensq.ordero.api.item.ItemGroupDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    private Integer id;
    private List<ItemGroupDto> itemGroupDtos ;
    private UUID customerID;
    private Integer price;

    private OrderDto(){}

    public static OrderDto orderDto(){
        return new OrderDto();
    }

    public OrderDto withId (Integer id){
        this.id = id;
        return this;
    }

    public OrderDto withItemGroup (List<ItemGroupDto> itemGroupDtos){
        this.itemGroupDtos = itemGroupDtos;
        return this;
    }

    public OrderDto withCustomerID (UUID customerID){
        this.customerID = customerID;
        return this;
    }

    public OrderDto withPrice (Integer price){
        this.price = price;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public List<ItemGroupDto> getItemGroupDtos() {
        return itemGroupDtos;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public Integer getPrice() {
        return price;
    }
}
