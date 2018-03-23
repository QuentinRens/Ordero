package com.rensq.ordero.api.item;

import java.math.BigDecimal;

public class ItemDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;

    private ItemDto(){}

    public static ItemDto itemDto(){
        return new ItemDto();
    }

    public ItemDto withID(Integer id) {
        this.id = id;
        return this;
    }

    public ItemDto withName(String name) {
        this.name = name;
        return this;
    }

    public ItemDto withDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemDto withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ItemDto withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }
}
