package com.rensq.ordero.api.order;

import java.util.Map;

public class OrderReportDto {
    private Map<String, OrderDto> orders;
    private Integer totalPrice;

    private OrderReportDto(){}

    public static OrderReportDto orderReportDto(){
        return new OrderReportDto();
    }

    public OrderReportDto withOrders (Map<String, OrderDto> orders){
        this.orders = orders;
        return this;
    }

    public OrderReportDto withTotalPrice (Integer totalPrice){
        this.totalPrice = totalPrice;
        return this;
    }

    public Map<String, OrderDto> getOrders() {
        return orders;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }
}
