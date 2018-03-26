package com.rensq.ordero.domain.item;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Item implements Comparable<Item> {
    private UUID id;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;
    private LocalDate lastOrdered;
    private StockResupplyUrgency stockResupplyUrgency;


    private Item() {
    }

    public void setId(UUID id) {
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

    public void setLastOrdered(LocalDate lastOrdered) {
        this.lastOrdered = lastOrdered;
    }

    public void setStockResupplyUrgency(StockResupplyUrgency stockResupplyUrgency) {
        this.stockResupplyUrgency = stockResupplyUrgency;
    }

    public UUID getId() {
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

    public LocalDate getLastOrdered() {
        return lastOrdered;
    }

    public StockResupplyUrgency getStockResupplyUrgency() {
        return stockResupplyUrgency;
    }

    @Override
    public int compareTo(Item o) {
        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_LOW && o.stockResupplyUrgency == StockResupplyUrgency.STOCK_LOW) {
            if (lastOrdered != null && o.lastOrdered == null) {
                return -1;
            }

            if (lastOrdered == null && o.lastOrdered != null) {
                return 1;
            }

            if (lastOrdered == null){
                if (amount < o.amount){
                    return -1;
                }

                if (amount > o.amount){
                    return 1;
                }
                return 0;
            }

            if (lastOrdered.isBefore(o.lastOrdered)) {
                return -1;
            }

            if (lastOrdered.isAfter(o.lastOrdered)) {
                return 1;
            }

            if (amount < o.amount) {
                return -1;
            }

            if (amount > o.amount) {
                return 1;
            }
            return 0;
        }

        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_MEDIUM && o.stockResupplyUrgency == StockResupplyUrgency.STOCK_MEDIUM) {
            if (lastOrdered != null && o.lastOrdered == null) {
                return -1;
            }

            if (lastOrdered == null && o.lastOrdered != null) {
                return 1;
            }

            if (lastOrdered == null){
                if (amount < o.amount){
                    return -1;
                }

                if (amount > o.amount){
                    return 1;
                }
                return 0;
            }

            if (lastOrdered.isBefore(o.lastOrdered)) {
                return -1;
            }

            if (lastOrdered.isAfter(o.lastOrdered)) {
                return 1;
            }

            if (amount < o.amount) {
                return -1;
            }

            if (amount > o.amount) {
                return 1;
            }
            return 0;
        }

        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_HIGH && o.stockResupplyUrgency == StockResupplyUrgency.STOCK_HIGH) {
            if (lastOrdered != null && o.lastOrdered == null) {
                return -1;
            }

            if (lastOrdered == null && o.lastOrdered != null) {
                return 1;
            }

            if (lastOrdered == null){
                if (amount < o.amount){
                    return -1;
                }

                if (amount > o.amount){
                    return 1;
                }
                return 0;
            }

            if (lastOrdered.isBefore(o.lastOrdered)) {
                return -1;
            }

            if (lastOrdered.isAfter(o.lastOrdered)) {
                return 1;
            }

            if (amount < o.amount) {
                return -1;
            }

            if (amount > o.amount) {
                return 1;
            }
            return 0;
        }
        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_LOW && o.getStockResupplyUrgency() != StockResupplyUrgency.STOCK_LOW) {
            return -1;
        }

        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_MEDIUM && o.getStockResupplyUrgency() == StockResupplyUrgency.STOCK_LOW) {
            return 1;
        }

        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_MEDIUM && o.getStockResupplyUrgency() == StockResupplyUrgency.STOCK_HIGH) {
            return -1;
        }
        if (stockResupplyUrgency == StockResupplyUrgency.STOCK_HIGH && o.getStockResupplyUrgency() != StockResupplyUrgency.STOCK_HIGH) {
            return 1;
        }
        return 0;
    }


    public static class ItemBuilder {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer amount;
        private LocalDate lastOrdered;
        private StockResupplyUrgency stockResupplyUrgency;
        ;

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
            item.setLastOrdered(lastOrdered);
            item.setStockResupplyUrgency(stockResupplyUrgency);
            return item;
        }

        public ItemBuilder withID(UUID id) {
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

        public ItemBuilder withLastOrdered(LocalDate lastOrdered) {
            this.lastOrdered = lastOrdered;
            return this;
        }

        public ItemBuilder withStockResupplyUrgency(StockResupplyUrgency stockResupplyUrgency) {
            this.stockResupplyUrgency = stockResupplyUrgency;
            return this;
        }
    }
}
