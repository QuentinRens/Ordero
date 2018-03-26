package com.rensq.ordero.api.item;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.item.ItemGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class)
public class ItemGroupMapperTest {

    @Inject
    private ItemGroupMapper itemGroupMapper;


    @Test
    public void toDto_givenItemGroup_thenMapAllFieldsToItemGroupDto() {
        ItemGroup itemGroup = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .withShippingAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().build())
                .withOrderId(UUID.randomUUID())
                .build();

        ItemGroupDto itemGroupDto = itemGroupMapper.toDto(itemGroup);

        assertThat(itemGroupDto).isEqualToIgnoringGivenFields(itemGroup, "price", "shippingDate", "shippingAddress", "orderId");
        assertThat(itemGroupDto.getPrice()).isPositive();
        assertThat(itemGroupDto.getShippingDate()).isNotEmpty();
    }

    @Test
    public void toDomain_givenItemGroupDto_thenMapAllFieldsToItemGroup() {
        ItemGroupDto itemGroupDto = ItemGroupDto.itemGroupDto()
                .withName("Toy")
                .withDescription("A big toy")
                .withAmount(10);

        ItemGroup itemGroup = itemGroupMapper.toDomain(itemGroupDto);

        assertThat(itemGroup).isEqualToIgnoringGivenFields(itemGroupDto, "orderId", "shippingAddress");
    }
}
