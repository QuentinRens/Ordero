package com.rensq.ordero.api.order;

import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.api.item.ItemGroupMapper;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/shipping")
public class ShippingController {
    private OrderService orderService;
    private OrderMapper orderMapper;
    private ItemGroupMapper itemGroupMapper;

    @Inject
    public ShippingController(OrderService orderService, OrderMapper orderMapper, ItemGroupMapper itemGroupMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.itemGroupMapper = itemGroupMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<OrderDto, List<ItemGroupDto>> getOrderWithItemGroupsToBeShippedToday(){
        Map<Order, List <ItemGroup>> toBeShipped = orderService.getOrderWithItemGroupsToBeShippedToday();

        List<OrderDto> orderDtos = toBeShipped.keySet().stream()
                .map(order -> orderMapper.toDto(order))
                .collect(Collectors.toList());

        List <ItemGroupDto> itemGroupDtos = toBeShipped.values().stream()
                .flatMap(list -> list.stream())
                .map(itemGroup -> itemGroupMapper.toDto(itemGroup))
                .collect(Collectors.toList());

        Map<OrderDto, List<ItemGroupDto>> theMap = new HashMap<>();
        for (OrderDto orderDto : orderDtos){
            theMap.put(orderDto, itemGroupDtos.stream()
                    .filter(itemGroupDto -> itemGroupDto.getOrderId().equals(orderDto.getId()))
                    .collect(Collectors.toList()));
        }
        return theMap;
    }

}
