package com.rensq.ordero.api.order;

import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.api.item.ItemGroupMapper;
import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class OrderMapper {
    private ItemGroupMapper itemGroupMapper;

    @Inject
    public OrderMapper(ItemGroupMapper itemGroupMapper) {
        this.itemGroupMapper = itemGroupMapper;
    }

    OrderDto toDto (Order order){
        List<ItemGroupDto> itemGroupDtos = order.getItemGroups().stream()
                .map(itemGroup -> itemGroupMapper.toDto(itemGroup))
                .collect(Collectors.toList());
        return OrderDto.orderDto().withId(order.getId()).withItemGroup(itemGroupDtos).withCustomerID(order.getCustomerId());
    }

    Order toDomain (OrderDto orderDto){
        List <ItemGroup> itemGroups = orderDto.getItemGroupDtos().stream()
                .map(itemGroupDto -> itemGroupMapper.toDomain(itemGroupDto))
                .collect(Collectors.toList());

        return Order.OrderBuilder.order()
                .withItemGroups(itemGroups)
                .withCustomerId(orderDto.getCustomerID())
                .build();
    }
}
