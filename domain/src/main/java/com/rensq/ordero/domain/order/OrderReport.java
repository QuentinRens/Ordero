package com.rensq.ordero.domain.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderReport {
    private Map<Integer, Order> orders;
    private BigDecimal totalPrice;

    private OrderReport(){}

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class OrderReportBuilder{
        private Map<Integer, Order> orders;
        private BigDecimal totalPrice;

        private OrderReportBuilder(){}

        public static OrderReportBuilder orderReport(){
            return new OrderReportBuilder();
        }

        public OrderReport build(){
            OrderReport orderReport = new OrderReport();
            orderReport.setOrders(orders);
            orderReport.setTotalPrice(totalPrice);
            return orderReport;
        }

        public OrderReportBuilder withOrders(List<Order> orders){
            Map<Integer, Order> ordersMap = new HashMap<>();
            for (int i = 0; i < orders.size(); i++){
                ordersMap.put(orders.get(i).getId(), orders.get(i));
            }
            this.orders = ordersMap;
            return this;
        }

        public OrderReportBuilder withTotalPrice(List<Order> orders){
            List<BigDecimal> orderPrices = orders.stream()
                    .map(order -> order.getPrice())
                    .collect(Collectors.toList());
            BigDecimal sum = new BigDecimal(0);
            for (int i =0; i <orderPrices.size(); i++){
                sum = sum.add(orderPrices.get(i));
            }
            this.totalPrice = sum;
            return this;
        }
    }
}
