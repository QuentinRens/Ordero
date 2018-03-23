package com.rensq.ordero.service.order;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderRepository;
import com.rensq.ordero.service.exceptions.EmptyFieldException;
import com.rensq.ordero.service.exceptions.EmptyRequestException;
import com.rensq.ordero.service.exceptions.EmptyRequestException.CrudAction;
import com.rensq.ordero.service.exceptions.UnknownResourceException;
import com.rensq.ordero.service.item.ItemService;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class OrderService {
    private OrderRepository orderRepository;
    private ItemService itemService;

    @Inject
    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public Order createOrder(Order order){
        assertItemGroupIsNotEmpty(order);
        assertItemGroupsHaveName(order);
        assertNoAmountIsNull(order);
        assertGroupsReferToExistingItems(order);
        setShippingDatesForItems(order);
        setPricesForItemGroups(order);
        return orderRepository.storeOrder(order);
    }

    private void assertGroupsReferToExistingItems(Order order) {
        List<String> itemGroupNames = order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getName())
                .collect(Collectors.toList());
        if (!itemService.getItemNames().containsAll(itemGroupNames)){
            throw new UnknownResourceException("itemgroupname", Item.class.getSimpleName());
        }
    }

    private void setPricesForItemGroups(Order order) {
        for (ItemGroup itemGroup : order.getItemGroups()){
            itemGroup.setPrice(itemService.getItemByName(itemGroup.getName()).getPrice().multiply(new BigDecimal(itemGroup.getAmount())));
        }
    }

    private void assertNoAmountIsNull(Order order) {
        if (!order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getAmount())
                .filter(integer -> integer == null ||integer == 0)
                .collect(Collectors.toList()).isEmpty()){
            throw new EmptyFieldException();
        }
    }

    private void assertItemGroupsHaveName(Order order) {
        if(order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getName())
                .anyMatch(name -> name == null)){
            throw new UnknownResourceException(Item.class.getSimpleName(),Item.class.getSimpleName() );
        }
    }

    private void assertItemGroupIsNotEmpty(Order order) {
        if (order.getItemGroups().isEmpty()){
            throw new EmptyRequestException(ItemGroup.class.getSimpleName(), CrudAction.CREATE );
        }
    }

    private void setShippingDatesForItems(Order order) {
        for (ItemGroup itemGroup :order.getItemGroups())
            if (itemGroup.getAmount() >=1){
                itemGroup.setShippingDate(LocalDate.now().plusDays(1));
            }else{
                itemGroup.setShippingDate(LocalDate.now().plusWeeks(1));
            }
    }
}
