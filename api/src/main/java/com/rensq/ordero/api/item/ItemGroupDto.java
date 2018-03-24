package com.rensq.ordero.api.item;

public class ItemGroupDto {
    private String name;
    private String description;
    private Integer price;
    private Integer amount;
    private String shippingDate;

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
}
