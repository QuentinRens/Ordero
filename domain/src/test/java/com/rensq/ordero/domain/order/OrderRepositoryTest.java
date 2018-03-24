package com.rensq.ordero.domain.order;

import com.rensq.ordero.domain.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderRepositoryTest {
    private OrderRepository orderRepository;

    @Before
    public void instantiateRepository(){
        orderRepository = new OrderRepository();
    }

    @Test
    public void getOrderByCustomerId_givenCustomerId_ShouldReturnAllOrdersForThatCustomerId(){
        UUID customerID = UUID.randomUUID();

        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).build());
        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID).build());
        orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(UUID.randomUUID()).build());

        Assertions.assertThat(orderRepository.getOrderByCustomerId(customerID).size()).isEqualTo(2);
    }


}
