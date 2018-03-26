package com.rensq.ordero.domain.order;

import com.rensq.ordero.domain.item.ItemGroup;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderRepositoryTest {
    private OrderRepository orderRepository;

    @Before
    public void instantiateRepository() {
        orderRepository = new OrderRepository();
    }

    @Test
    public void getOrderByCustomerId_givenCustomerId_ShouldReturnAllOrdersForThatCustomerId() {
        UUID customerID = UUID.randomUUID();

        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);

        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).withItemGroups(itemGroups).build());
        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).withItemGroups(itemGroups).build());
        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(UUID.randomUUID()).withItemGroups(itemGroups).build());

        Assertions.assertThat(orderRepository.getOrderByCustomerId(customerID).size()).isEqualTo(2);
    }

    @Test
    public void getOrderWithItemGroupsToBeShippedToday_GivenItemToBeShippedToday_ShouldReturnOrdersContainingThoseItems(){
        UUID customerID = UUID.randomUUID();

        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        ItemGroup itemGroup3 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Bike")
                .withDescription("A big bike")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        ItemGroup itemGroup2 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now().plusDays(6))
                .build();

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);
        itemGroups.add(itemGroup2);


        List<ItemGroup> itemGroups2 = new ArrayList<>();
        itemGroups2.add(itemGroup3);

        List<ItemGroup> itemGroups3 = new ArrayList<>();
        itemGroups3.add(itemGroup2);


        Order order1 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).withItemGroups(itemGroups).build());
        Order order2 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).withItemGroups(itemGroups2).build());
        Order order3 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).withItemGroups(itemGroups3).build());

        Map<Order, List<ItemGroup>> actualOrdersAndItemsToBeShipped = orderRepository.getOrderWithItemGroupsToBeShippedToday();

        Assertions.assertThat(actualOrdersAndItemsToBeShipped.keySet()).containsExactlyInAnyOrder(order1, order2);
        Assertions.assertThat(actualOrdersAndItemsToBeShipped.get(order1)).containsExactly(itemGroup1);
        Assertions.assertThat(actualOrdersAndItemsToBeShipped.get(order2)).containsExactly(itemGroup3);

    }


}
