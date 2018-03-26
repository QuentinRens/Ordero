package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.StockResupplyUrgency;

import java.math.BigDecimal;

public class ItemDto {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;
    private String lastOrdered;
    private StockResupplyUrgency stockResupplyUrgency;

    private ItemDto(){}

    public static ItemDto itemDto(){
        return new ItemDto();
    }

    public ItemDto withID(String id) {
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

    public  ItemDto withLastOrdered (String lastOrdered){
        this.lastOrdered = lastOrdered;
        return this;
    }
    public  ItemDto withStockResupplyUrgency (StockResupplyUrgency stockResupplyUrgency){
        this.stockResupplyUrgency = stockResupplyUrgency;
        return this;
    }

    public String  getId() {
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

    public String getLastOrdered() {
        return lastOrdered;
    }

    public StockResupplyUrgency getStockResupplyUrgency() {
        return stockResupplyUrgency;
    }
}
