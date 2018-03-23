package com.rensq.ordero.service.item;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService ItemService;

    @Test
    public void createItem_HappyPath() {
        Item providedItem = Item.ItemBuilder.item()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .build();

        Item expectedItem = Item.ItemBuilder.item()
                .withID(1)
                .withName(providedItem.getName())
                .withDescription(providedItem.getDescription())
                .withPrice(providedItem.getPrice())
                .withAmount(providedItem.getAmount())
                .build();

        Mockito.when(itemRepository.storeItem(providedItem)).thenReturn(expectedItem);

        Item actualItem = ItemService.createItem(providedItem);

        Assertions.assertThat(actualItem).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    public void updateItem_HappyPath(){
        Item providedItem = Item.ItemBuilder.item()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10)
                .build();

        Item expectedItem = Item.ItemBuilder.item()
                .withID(42)
                .withName(providedItem.getName())
                .withDescription(providedItem.getDescription())
                .withPrice(providedItem.getPrice())
                .withAmount(providedItem.getAmount())
                .build();

        Mockito.when(itemRepository.getItem(42)).thenReturn(Item.ItemBuilder.item().withID(42).build());
        Mockito.when(itemRepository.updateItem(providedItem)).thenReturn(expectedItem);

        Item actualItem = ItemService.updateItem(42, providedItem);

        Assertions.assertThat(actualItem).isEqualToComparingFieldByField(expectedItem);
    }
}
