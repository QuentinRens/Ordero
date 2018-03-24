package com.rensq.ordero.api.order;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerRepository;
import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
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
public class OrderCreationControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Test
    public void makeOrder(){
        Customer storedCustomer = Customer.CustomerBuilder.customer().withFirstName("Arnold").build();
        customerRepository.storeCustomer(storedCustomer);

        itemRepository.storeItem(Item.ItemBuilder.item().withName("Toy").withAmount(6).withPrice(new BigDecimal(20)).build());
        itemRepository.storeItem(Item.ItemBuilder.item().withName("Bike").withAmount(0).withPrice(new BigDecimal(10)).build());


        ItemGroupDto itemGroupDto1 = ItemGroupDto.itemGroupDto()
                .withName("Toy")
                .withDescription("A big toy")
                .withAmount(10);

        ItemGroupDto itemGroupDto2 = ItemGroupDto.itemGroupDto()
                .withName("Bike")
                .withDescription("A big bike")
                .withAmount(10);

        List<ItemGroupDto> itemGroupDtoss = new ArrayList<>();
        itemGroupDtoss.add(itemGroupDto1);
        itemGroupDtoss.add(itemGroupDto2);


        OrderDto actualOrderDto = new TestRestTemplate()
                .postForObject(String.format("http://localhost:%s/%s", port, "order_creation"),
                        OrderDto.orderDto().withItemGroup(itemGroupDtoss).withCustomerID(storedCustomer.getId()), OrderDto.class);

        Assertions.assertThat(actualOrderDto.getId()).isEqualTo(1);
        Assertions.assertThat(actualOrderDto.getCustomerID()).isEqualTo(storedCustomer.getId());
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().size()).isEqualTo(2);
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(0)).isEqualToIgnoringGivenFields(itemGroupDto1,"price", "shippingDate");
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1)).isEqualToIgnoringGivenFields(itemGroupDto2,"price", "shippingDate");
/*        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(0).getPrice()).isEqualTo(200);
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1).getPrice()).isEqualTo(100);*/
        Assertions.assertThat(actualOrderDto.getItemGroupDtos().get(1).getPrice()).isPositive();
        Assertions.assertThat(LocalDate.parse(actualOrderDto.getItemGroupDtos().get(0).getShippingDate())).isBefore(LocalDate.now().plusDays(2));
        Assertions.assertThat(LocalDate.parse(actualOrderDto.getItemGroupDtos().get(1).getShippingDate())).isAfter(LocalDate.now().plusDays(2));
    }




}
