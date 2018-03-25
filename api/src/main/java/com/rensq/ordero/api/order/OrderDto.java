package com.rensq.ordero.api.order;


import com.rensq.ordero.api.item.ItemGroupDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    private String id;
    private List<ItemGroupDto> itemGroupDtos ;
    private String customerID;
    private Integer price;

    private OrderDto(){}

    public static OrderDto orderDto(){
        return new OrderDto();
    }

    public OrderDto withId (String id){
        this.id = id;
        return this;
    }

    public OrderDto withItemGroup (List<ItemGroupDto> itemGroupDtos){
        this.itemGroupDtos = itemGroupDtos;
        return this;
    }

    public OrderDto withCustomerID (String customerID){
        this.customerID = customerID;
        return this;
    }

    public OrderDto withPrice (Integer price){
        this.price = price;
        return this;
    }

    public String getId() {
        return id;
    }

    public List<ItemGroupDto> getItemGroupDtos() {
        return itemGroupDtos;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Integer getPrice() {
        return price;
    }
}
