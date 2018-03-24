package com.rensq.ordero.service.order;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderReport;
import com.rensq.ordero.domain.order.OrderRepository;
import com.rensq.ordero.service.customer.CustomerService;
import com.rensq.ordero.service.item.ItemService;
import com.rensq.ordero.service.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void createOrder_HappyPath(){
        List<ItemGroup> givenItemGroups = new ArrayList<>();
        givenItemGroups.add(ItemGroup.ItemGroupBuilder.itemGroup().withName("Toy").withAmount(2).build());
        givenItemGroups.add(ItemGroup.ItemGroupBuilder.itemGroup().withName("Machine").withAmount(3).build());
        givenItemGroups.add(ItemGroup.ItemGroupBuilder.itemGroup().withName("Bike").withAmount(2).build());
        Order givenOrder = Order.OrderBuilder.order().withItemGroups(givenItemGroups).build();

        List<String> listOfNamesInRepo = new ArrayList<>();
        listOfNamesInRepo.add("Toy");
        listOfNamesInRepo.add("Machine");
        listOfNamesInRepo.add("Bike");
        UUID givenCustomerID = UUID.randomUUID();
        Customer expectedCustomer = Customer.CustomerBuilder.customer().withID(givenCustomerID).build();

        Mockito.when(itemService.getItemNames()).thenReturn(listOfNamesInRepo);
        Mockito.when(itemService.getItemByName(Mockito.anyString())).thenReturn(Item.ItemBuilder.item().withAmount(1).withPrice(new BigDecimal(1)).build());
        Mockito.when(orderRepository.storeOrder(givenOrder)).thenReturn(givenOrder);
        Mockito.when(customerService.getCustomer(givenCustomerID)).thenReturn(expectedCustomer);


        Order actualOrder = orderService.createOrder(givenOrder, givenCustomerID );

        Assertions.assertThat(actualOrder.getItemGroups().get(1).getPrice()).isPositive();
        Assertions.assertThat(actualOrder.getItemGroups().get(1).getShippingDate()).isBetween(LocalDate.now(), LocalDate.now().plusDays(2));
        Assertions.assertThat(actualOrder.getCustomerId()).isEqualTo(givenCustomerID);
        Assertions.assertThat(actualOrder.getPrice().intValue()).isEqualTo(7);
    }

    @Test
    public void getOrderReport_HappyPath(){
        UUID customerID = UUID.randomUUID();

        Order order1 = Order.OrderBuilder.order().withCustomerId(customerID).withId(1).withPrice(new BigDecimal(1)).build();
        Order order2 = Order.OrderBuilder.order().withCustomerId(customerID).withId(2).withPrice(new BigDecimal(1)).build();
        Order order3 = Order.OrderBuilder.order().withCustomerId(customerID).withId(3).withPrice(new BigDecimal(1)).build();

        List <Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        Mockito.when(orderRepository.getOrderByCustomerId(customerID)).thenReturn(orders);

        OrderReport actualReport = orderService.getOrderReport(customerID.toString());

        Assertions.assertThat(actualReport.getOrders().keySet()).containsExactly(1,2,3);
        Assertions.assertThat(actualReport.getOrders().values()).containsExactly (order1, order2, order3);
        Assertions.assertThat(actualReport.getTotalPrice().intValue()).isEqualTo(3);


    }
}
