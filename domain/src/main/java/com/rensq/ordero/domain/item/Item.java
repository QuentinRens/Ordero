package com.rensq.ordero.domain.item;


import java.math.BigDecimal;

public class Item {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;

    private Item() {
    }

    public void setId(Integer id) {
        this.id = id;
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

    public static class ItemBuilder {
        private Integer id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer amount;

        private ItemBuilder() {
        }

        public static ItemBuilder item() {
            return new ItemBuilder();
        }

        public Item build() {
            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setAmount(amount);
            return item;
        }

        public ItemBuilder withID(Integer id) {
            this.id = id;
            return this;
        }

        public ItemBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ItemBuilder withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }
    }
}
