package com.rensq.ordero.api.order;


import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.customer.CustomerRepository;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderReportControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private OrderRepository orderRepository;

    @Test
    public void getOrderReport(){
        Customer storedCustomer = Customer.CustomerBuilder.customer().withFirstName("Arnold").build();
        customerRepository.storeCustomer(storedCustomer);

        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .withShippingAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().build())
                .withOrderId(UUID.randomUUID())
                .build();

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);

        Order order1 = Order.OrderBuilder.order().withCustomerId(storedCustomer.getId()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();
        Order order2 = Order.OrderBuilder.order().withCustomerId(storedCustomer.getId()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();
        Order order3 = Order.OrderBuilder.order().withCustomerId(storedCustomer.getId()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();

        orderRepository.storeOrder(order1);
        orderRepository.storeOrder(order2);
        orderRepository.storeOrder(order3);

        OrderReportDto orderReportDto = new TestRestTemplate()
                .getForObject(String.format("http://localhost:%s/%s/%s", port, "order_report", storedCustomer.getId().toString()) ,
                        OrderReportDto.class);

        Assertions.assertThat(orderReportDto.getTotalPrice().intValue()).isEqualTo(3);
        Assertions.assertThat(orderReportDto.getOrders().values().size()).isEqualTo(3);
    }
}
