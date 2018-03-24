package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.ItemGroup;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemGroupMapperTest {
    private ItemGroupMapper itemGroupMapper;

    @Before
    public void instantiateMapper() {
        itemGroupMapper = new ItemGroupMapper();
    }

    @Test
    public void toDto_givenItemGroup_thenMapAllFieldsToItemGroupDto() {
        ItemGroup itemGroup = ItemGroup.ItemGroupBuilder.itemGroup()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withShippingDate(LocalDate.now())
                .build();

        ItemGroupDto itemGroupDto = itemGroupMapper.toDto(itemGroup);

        assertThat(itemGroupDto).isEqualToIgnoringGivenFields(itemGroup, "price", "shippingDate");
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

        assertThat(itemGroup).isEqualToComparingFieldByField(itemGroupDto);
    }
}
