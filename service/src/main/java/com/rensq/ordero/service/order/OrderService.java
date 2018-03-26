package com.rensq.ordero.service.order;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderReport;
import com.rensq.ordero.domain.order.OrderRepository;
import com.rensq.ordero.service.customer.CustomerService;
import com.rensq.ordero.service.exceptions.EmptyFieldException;
import com.rensq.ordero.service.exceptions.EmptyRequestException;
import com.rensq.ordero.service.exceptions.EmptyRequestException.CrudAction;
import com.rensq.ordero.service.exceptions.UnknownResourceException;
import com.rensq.ordero.service.item.ItemService;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
public class OrderService {
    private OrderRepository orderRepository;
    private ItemService itemService;
    private CustomerService customerService;

    @Inject
    public OrderService(OrderRepository orderRepository, ItemService itemService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.customerService = customerService;
    }

    public Order getOrder (UUID orderId){
        assertOrderExistForId(orderId);
        return orderRepository.getOrder(orderId);
    }

    private void assertOrderExistForId(UUID orderId) {
        if (orderRepository.getOrder(orderId) == null){
            throw new UnknownResourceException("ID", Order.class.getSimpleName());
        }
    }

    public Order createOrder(Order order, UUID providedCustomerId){
        assertItemGroupIsNotEmpty(order);
        assertItemGroupsHaveName(order);
        assertNoAmountIsNull(order);
        assertGroupsReferToExistingItems(order);
        setShippingDatesForItems(order);
        setShippingAddressForItems(order, providedCustomerId);
        setPricesForItemGroups(order);
        setDescriptionForItemGroup(order);
        setOrderPrice(order);
        order.setCustomerId(customerService.getCustomer(providedCustomerId).getId());
        updateItems(order);
        return orderRepository.storeOrder(order);
    }


    public Order makeReorder(String orderId, String customerId){
        assertOrdersExistForCustomerId(customerId);
        List<Order> customerOrders = orderRepository.getOrderByCustomerId(UUID.fromString(customerId));
        assertOrderIdIsValid(orderId, customerOrders);
        Order reorder = customerOrders.stream().
                filter(order -> order.getId().equals(UUID.fromString(orderId)))
                .findFirst()
                .orElse(null);
        return orderRepository.storeOrder(reorder);
    }

    public Map<Order, List<ItemGroup>> getOrderWithItemGroupsToBeShippedToday(){
        return orderRepository.getOrderWithItemGroupsToBeShippedToday();
    }
    private void assertOrderIdIsValid(String orderId, List<Order> customerOrders) {
        if (orderId == null){
            throw new EmptyFieldException();
        }
        if (customerOrders.stream().
                filter(order -> order.getId().equals(UUID.fromString(orderId)))
                .findFirst()
                .orElse(null) == null){
            throw new UnknownResourceException("ID", Order.class.getSimpleName());
        }
    }

    public OrderReport getOrderReport(String customerId){
        assertOrdersExistForCustomerId(customerId);
        List<Order> ordersForCustomer = orderRepository.getOrderByCustomerId(UUID.fromString(customerId));
        return OrderReport.OrderReportBuilder.orderReport()
                .withOrders(ordersForCustomer)
                .withTotalPrice(ordersForCustomer)
                .build();
    }

    private void assertOrdersExistForCustomerId(String customerId) {
        if (orderRepository.getOrderByCustomerId(UUID.fromString(customerId)).isEmpty()){
            throw new UnknownResourceException("Customer ID", Order.class.getSimpleName());
        }
    }

    private void setOrderPrice(Order order) {
        List<BigDecimal> itemGroupPrices = order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getPrice())
                .collect(Collectors.toList());
        BigDecimal totalPrice = new BigDecimal(0);
        for (int i = 0; i < itemGroupPrices.size(); i++){
            totalPrice = totalPrice.add(itemGroupPrices.get(i));
        }
        order.setPrice(totalPrice);
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
        if (order.getItemGroups().isEmpty() || order.getItemGroups() == null){
            throw new EmptyRequestException(ItemGroup.class.getSimpleName(), CrudAction.CREATE );
        }
    }

    private void setShippingDatesForItems(Order order) {
        for (ItemGroup itemGroup :order.getItemGroups())
            if (itemService.getItemByName(itemGroup.getName()).getAmount() >=1){
                itemGroup.setShippingDate(LocalDate.now().plusDays(1));
            }else{
                itemGroup.setShippingDate(LocalDate.now().plusWeeks(1));
            }
    }

    private void setShippingAddressForItems(Order order, UUID providedCustomerId) {
        for (ItemGroup itemGroup :order.getItemGroups()) {
            itemGroup.setShippingAddress(customerService.getCustomer(providedCustomerId).getCustomerAddress());
        }
    }

    private void setDescriptionForItemGroup(Order order) {
        List<Item> orderedItems = order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getName())
                .map(s -> itemService.getItemByName(s)).collect(Collectors.toList());
        for (ItemGroup itemGroup :order.getItemGroups()){
            itemGroup.setDescription(itemService.getItemByName(itemGroup.getName()).getDescription());
        }
    }


    private void updateItems(Order order) {
        List<Item> orderedItems = order.getItemGroups().stream()
                .map(itemGroup -> itemGroup.getName())
                .map(s -> itemService.getItemByName(s)).collect(Collectors.toList());
        for (Item item : orderedItems){
            ItemGroup correspondingItemGroup = order.getItemGroups().stream()
                    .filter(itemGroup -> itemGroup.getName() == item.getName())
                    .findFirst()
                    .orElse(null);
            if (correspondingItemGroup != null){
                if (correspondingItemGroup.getAmount() > item.getAmount()){
                    item.setAmount(0);
                } else {
                    item.setAmount(item.getAmount() - correspondingItemGroup.getAmount());
                }
            }
            item.setLastOrdered(LocalDate.now());
            itemService.updateItem(item.getId().toString(), item);
        }
    }
}
