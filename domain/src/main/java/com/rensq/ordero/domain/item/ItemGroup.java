package com.rensq.ordero.domain.item;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemGroup {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;
    private LocalDate shippingDate;

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

    public static class ItemGroupBuilder {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer amount;
        private LocalDate shippingDate;

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
    }
}
