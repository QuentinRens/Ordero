package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.StockResupplyUrgency;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        LocalDate now = LocalDate.now();
        Item item = Item.ItemBuilder.item()
                .withID(UUID.randomUUID())
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .withLastOrdered(now)
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_HIGH)
                .build();

        ItemDto itemDto = itemMapper.toDto(item);

        assertThat(itemDto).isEqualToIgnoringGivenFields(item, "id", "lastOrdered");
        assertThat(itemDto.getLastOrdered()).isEqualTo(now.format(DateTimeFormatter.ISO_DATE));
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
