package com.rensq.ordero.api.order;

import com.rensq.ordero.domain.order.OrderReport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
public class OrderReportMapper {
    private OrderMapper orderMapper;

   @Inject
    public OrderReportMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    OrderReportDto toDto(OrderReport orderReport){
        List<OrderDto> orderDtos = orderReport.getOrders().values().stream()
                .map(order -> orderMapper.toDto(order))
                .collect(Collectors.toList());
        Map<String, OrderDto> orders = new HashMap<>();
        for (int i = 0; i < orderDtos.size(); i++){
            orders.put(orderDtos.get(i).getId(), orderDtos.get(i));
        }
        return OrderReportDto.orderReportDto().withOrders(orders).withTotalPrice(orderReport.getTotalPrice().intValue());
    }
}
