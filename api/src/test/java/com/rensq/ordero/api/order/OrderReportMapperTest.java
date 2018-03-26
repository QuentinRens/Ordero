package com.rensq.ordero.api.order;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
import com.rensq.ordero.domain.order.OrderReport;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class)
public class OrderReportMapperTest {

    @Inject
    private OrderMapper orderMapper;

    @Inject
    private OrderReportMapper orderReportMapper;

    @Test
    public void toDto (){
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

        Order order1 = Order.OrderBuilder.order().withId(UUID.randomUUID()).withCustomerId(UUID.randomUUID()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();
        Order order2 = Order.OrderBuilder.order().withId(UUID.randomUUID()).withCustomerId(UUID.randomUUID()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();
        Order order3 = Order.OrderBuilder.order().withId(UUID.randomUUID()).withCustomerId(UUID.randomUUID()).withPrice(new BigDecimal(1)).withItemGroups(itemGroups).build();

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        OrderReport report = OrderReport.OrderReportBuilder.orderReport().withTotalPrice(orders).withOrders(orders).build();

        OrderReportDto actualReport = orderReportMapper.toDto(report);

        Assertions.assertThat(actualReport.getOrders().keySet().size()).isEqualTo(3);
        Assertions.assertThat(actualReport.getTotalPrice().intValue()).isEqualTo(3);


    }


}
