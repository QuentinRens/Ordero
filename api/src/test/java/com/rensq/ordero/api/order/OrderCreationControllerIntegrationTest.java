package com.rensq.ordero.api.order;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.customer.CustomerRepository;
import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.domain.order.Order;
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
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderCreationControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private OrderService orderService;

    @Before
    public void emptyRepository(){
        itemRepository.clear();
    }

    @Test
    public void makeOrder(){
        CustomerAddress address = CustomerAddress.CustomerAddressBuilder.customerAddress()
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120")
                .build();

        Customer storedCustomer = Customer.CustomerBuilder.customer().withFirstName("Arnold").withCustomerAddress(address).build();
        customerRepository.storeCustomer(storedCustomer);

        itemRepository.storeItem(Item.ItemBuilder.item().withName("Toy").withDescription("A big toy").withAmount(6).withPrice(new BigDecimal(20)).build());
        itemRepository.storeItem(Item.ItemBuilder.item().withName("Bike").withDescription("A big bike").withAmount(0).withPrice(new BigDecimal(10)).build());


        ItemGroupDto itemGroupDto1 = ItemGroupDto.itemGroupDto()
                .withName("Toy")
                .withDescription("fgndgfjkgnjkdf")
                .withAmount(10);

        ItemGroupDto itemGroupDto2 = ItemGroupDto.itemGroupDto()
                .withName("Bike")
                .withDescription("skhekhgkjdg")
                .withAmount(10);

        List<ItemGroupDto> itemGroupDtoss = new ArrayList<>();
        itemGroupDtoss.add(itemGroupDto1);
        itemGroupDtoss.add(itemGroupDto2);


        OrderDto actualOrderDto = new TestRestTemplate()
                .postForObject(String.format("http://localhost:%s/%s", port, "order_creation"),
                        OrderDto.orderDto().withItemGroup(itemGroupDtoss).withCustomerID(storedCustomer.getId().toString()), OrderDto.class);

        Assertions.assertThat(actualOrderDto.getId()).isNotNull();
        Assertions.assertThat(actualOrderDto.getCustomerID()).isEqualTo(storedCustomer.getId().toString());
        Assertions.assertThat(actualOrderDto.getPrice()).isPositive();
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().size()).isEqualTo(2);
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(0)).isEqualToIgnoringGivenFields(itemGroupDto1,"price", "shippingDate", "description", "shippingAddress", "orderId");
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1)).isEqualToIgnoringGivenFields(itemGroupDto2,"price", "shippingDate", "description", "shippingAddress", "orderId");
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(0).getDescription()).isEqualTo("A big toy");
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1).getDescription()).isEqualTo("A big bike");
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1).getPrice()).isPositive();
        Assertions.assertThat(LocalDate.parse(actualOrderDto.getItemGroupDtos().get(0).getShippingDate())).isBefore(LocalDate.now().plusDays(2));
        Assertions.assertThat(LocalDate.parse(actualOrderDto.getItemGroupDtos().get(1).getShippingDate())).isAfter(LocalDate.now().plusDays(2));
    }

    @Test
    public void makeReorder(){
        CustomerAddress address = CustomerAddress.CustomerAddressBuilder.customerAddress()
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120")
                .build();

        Customer storedCustomer = customerRepository.storeCustomer(Customer.CustomerBuilder.customer().withFirstName("Arnold").withCustomerAddress(address).build());

        itemRepository.storeItem(Item.ItemBuilder.item().withName("Toy").withDescription("A big toy").withAmount(6).withPrice(new BigDecimal(20)).build());

        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withAmount(10)
                .build();

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);

        Order order1 = orderService.createOrder(Order.OrderBuilder.order().withItemGroups(itemGroups).build(), storedCustomer.getId());



        OrderDto actualOrderDto = new TestRestTemplate()
                .postForObject(String.format("http://localhost:%s/%s/%s/%s", port, "order_creation", order1.getId().toString(), storedCustomer.getId().toString()),
                        null,  OrderDto.class,
                        order1.getId().toString(),
                        order1.getCustomerId().toString());

        Assertions.assertThat(actualOrderDto.getId()).isNotEqualTo(order1.getId());
        Assertions.assertThat(actualOrderDto.getCustomerID()).isEqualTo(storedCustomer.getId().toString());
        Assertions.assertThat(actualOrderDto.getPrice()).isEqualTo(order1.getPrice().intValue());
    }




}
