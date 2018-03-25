package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemMapperTest {
    private ItemMapper itemMapper;

    @Before
    public void instantiateMapper() {
        itemMapper = new ItemMapper();
    }

    @Test
    public void toDto_givenItem_thenMapAllFieldsToItemDto() {
        Item item = Item.ItemBuilder.item()
                .withID(UUID.randomUUID())
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .build();

        ItemDto itemDto = itemMapper.toDto(item);

        assertThat(itemDto).isEqualToIgnoringGivenFields(item, "id");
    }

    @Test
    public void toDomain_givenItemDto_thenMapAllFieldsToItem() {
        ItemDto itemDto = ItemDto.itemDto()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10);

        Item item = itemMapper.toDomain(itemDto);

        assertThat(item).isEqualToComparingFieldByField(itemDto);
    }



}
