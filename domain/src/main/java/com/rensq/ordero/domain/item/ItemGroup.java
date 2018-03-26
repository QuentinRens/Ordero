package com.rensq.ordero.domain.item;

import com.rensq.ordero.domain.customer.CustomerAddress;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;
    private LocalDate shippingDate;
    private CustomerAddress shippingAddress;
    private UUID orderId;

    private ItemGroup() {
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
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

    public UUID getOrderId() {
        return orderId;
    }

    public CustomerAddress getShippingAddress() {
        return shippingAddress;
    }

    public static class ItemGroupBuilder {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer amount;
        private LocalDate shippingDate;
        private CustomerAddress shippingAddress;
        private UUID orderId;

        private ItemGroupBuilder() {
        }

        public static ItemGroupBuilder itemGroup() {
            return new ItemGroupBuilder();
        }

        public ItemGroup build() {
            ItemGroup itemGroup = new ItemGroup();
            itemGroup.setName(name);
            itemGroup.setDescription(description);
            itemGroup.setPrice(price);
            itemGroup.setAmount(amount);
            itemGroup.setShippingDate(shippingDate);
            itemGroup.setShippingAddress(shippingAddress);
            itemGroup.setOrderId(orderId);
            return itemGroup;
        }

        public ItemGroupBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ItemGroupBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemGroupBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ItemGroupBuilder withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ItemGroupBuilder withShippingDate (LocalDate shippingDate){
            this.shippingDate = shippingDate;
            return this;
        }

        public ItemGroupBuilder withShippingAddress (CustomerAddress shippingAddress){
            this.shippingAddress = shippingAddress;
            return this;
        }

        public  ItemGroupBuilder withOrderId (UUID orderId){
            this.orderId = orderId;
            return this;
        }
    }
}
