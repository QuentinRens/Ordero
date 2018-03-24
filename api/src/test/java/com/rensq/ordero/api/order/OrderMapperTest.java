package com.rensq.ordero.api.order;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.api.item.ItemGroupDto;
import com.rensq.ordero.api.item.ItemGroupMapper;
import com.rensq.ordero.domain.item.ItemGroup;
import com.rensq.ordero.domain.order.Order;
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
public class OrderMapperTest {

    @Inject
    private OrderMapper orderMapper;

    @Inject
    private ItemGroupMapper itemGroupMapper;

    @Test
    public void toDto () {
        ItemGroup itemGroup1 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        ItemGroup itemGroup2 = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Bike")
                .withDescription("A big bike")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        ItemGroupDto itemGroupDto1 = itemGroupMapper.toDto(itemGroup1);
        ItemGroupDto itemGroupDto2 = itemGroupMapper.toDto(itemGroup2);

        List<ItemGroup> itemGroups = new ArrayList<>();
        itemGroups.add(itemGroup1);
        itemGroups.add(itemGroup2);

        Order givenOrder = Order.OrderBuilder.order().withId(1).withItemGroups(itemGroups).withCustomerId(UUID.randomUUID()).build();

        OrderDto orderDto = orderMapper.toDto(givenOrder);

        Assertions.assertThat(orderDto.getItemGroupDtos().get(0)).isEqualToIgnoringGivenFields(itemGroup1, "shippingDate", "price");
        Assertions.assertThat(orderDto.getItemGroupDtos().get(1)).isEqualToIgnoringGivenFields(itemGroup2, "shippingDate", "price");
    }

    @Test
    public void toDomain () {
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

        OrderDto givenOrderDto = OrderDto.orderDto().withItemGroup(itemGroupDtoss).withCustomerID(UUID.randomUUID());

        Order actualOrder = orderMapper.toDomain(givenOrderDto);

        Assertions.assertThat(actualOrder.getItemGroups().get(0)).isEqualToComparingFieldByField(itemGroupDto1);
        Assertions.assertThat(actualOrder.getItemGroups().get(1)).isEqualToComparingFieldByField(itemGroupDto2);
    }





}
