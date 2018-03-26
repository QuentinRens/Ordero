package com.rensq.ordero.api.item;

import com.rensq.ordero.api.customer.CustomerAddressDto;

public class ItemGroupDto {
    private String name;
    private String description;
    private Integer price;
    private Integer amount;
    private String shippingDate;
    private CustomerAddressDto shippingAddress;
    private String orderId;


    private ItemGroupDto(){}

    public static ItemGroupDto itemGroupDto(){
        return new ItemGroupDto();
    }

    public ItemGroupDto withName(String name) {
        this.name = name;
        return this;
    }

    public ItemGroupDto withDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemGroupDto withPrice(Integer price) {
        this.price = price;
        return this;
    }

    public ItemGroupDto withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public ItemGroupDto withShippingDate (String shippingDate){
        this.shippingDate = shippingDate;
        return this;
    }

    public ItemGroupDto withShippingAddress (CustomerAddressDto shippingAddress){
        this.shippingAddress = shippingAddress;
        return this;
    }

    public ItemGroupDto withOrderId (String orderId){
        this.orderId = orderId;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public CustomerAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }
}
