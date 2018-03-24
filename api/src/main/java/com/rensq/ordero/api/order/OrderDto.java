package com.rensq.ordero.api.order;


import com.rensq.ordero.api.item.ItemGroupDto;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Integer id;
    private List<ItemGroupDto> itemGroupDtos ;

    public Integer getId() {
        return id;
    }

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

    public List<ItemGroupDto> getItemGroupDtos() {
        return itemGroupDtos;
    }
}
