package com.rensq.ordero.api.order;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.customer.CustomerRepository;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderRepository;
import com.rensq.ordero.service.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShippingControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderService orderService;

    @Before
    public void clearRepository(){
        orderRepository.clear();
    }

    @Test
    public void getOrderWithItemGroupsToBeShippedToday(){
        CustomerAddress address = CustomerAddress.CustomerAddressBuilder.customerAddress()
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120")
                .build();

        UUID customerID1 = UUID.randomUUID();
        UUID customerID2 = UUID.randomUUID();
        UUID customerID3 = UUID.randomUUID();

        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .withShippingAddress(address)
                .withOrderId(UUID.randomUUID())
                .build();

        ItemGroup itemGroup2 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .withShippingAddress(address)
                .withOrderId(UUID.randomUUID())
                .build();

        ItemGroup itemGroup3 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now().plusDays(6))
                .withShippingAddress(address)
                .withOrderId(UUID.randomUUID())
                .build();

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);
        itemGroups.add(itemGroup2);

        List<ItemGroup> itemGroups2 = new ArrayList<>();
        itemGroups2.add(itemGroup2);

        List<ItemGroup> itemGroups3 = new ArrayList<>();
        itemGroups3.add(itemGroup1);
        itemGroups3.add(itemGroup3);


        Order order1 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID1).withItemGroups(itemGroups).withPrice(new BigDecimal(20)).build());
        Order order2 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID2).withItemGroups(itemGroups2).withPrice(new BigDecimal(20)).build());
        Order order3 = orderRepository.storeOrder(Order.OrderBuilder.order().withCustomerId(customerID3).withItemGroups(itemGroups3).withPrice(new BigDecimal(20)).build());

        Map<OrderDto, List<ItemGroupDto>> actual = new TestRestTemplate().getForObject(String.format("http://localhost:%s/%s", port, "shipping"), Map.class);

        Assertions.assertThat(actual.keySet().size()).isEqualTo(2);

    }



}
