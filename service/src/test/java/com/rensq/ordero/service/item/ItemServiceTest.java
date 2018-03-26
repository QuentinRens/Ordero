package com.rensq.ordero.service.item;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.domain.item.StockResupplyUrgency;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .withID(UUID.randomUUID())
                .withName(providedItem.getName())
                .withDescription(providedItem.getDescription())
                .withPrice(providedItem.getPrice())
                .withAmount(providedItem.getAmount())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_HIGH)
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
                .withAmount(4)
                .withLastOrdered(LocalDate.now().minusDays(3))
                .build();

        String givenID = UUID.randomUUID().toString();
        UUID expectedID = UUID.fromString(givenID);


        Item expectedItem = Item.ItemBuilder.item()
                .withID(expectedID)
                .withName(providedItem.getName())
                .withDescription(providedItem.getDescription())
                .withPrice(providedItem.getPrice())
                .withAmount(providedItem.getAmount())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW)
                .build();

        Mockito.when(itemRepository.getItem(expectedID)).thenReturn(Item.ItemBuilder.item().withID(expectedID).build());
        Mockito.when(itemRepository.updateItem(providedItem)).thenReturn(expectedItem);

        Item actualItem = ItemService.updateItem(givenID, providedItem);

        Assertions.assertThat(actualItem).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    public void getItemByName_HappyPath(){
        List<Item> expectedItems = new ArrayList<>();
        expectedItems.add(Item.ItemBuilder.item().withName("Georgi").build());
        Mockito.when(itemRepository.getItems()).thenReturn(expectedItems);

        ItemService.getItemByName("Georgi");

        Mockito.verify(itemRepository).getItemByName("Georgi");
    }

    @Test
    public void getItemNames_HappyPath(){
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Georgi");

        Mockito.when(itemRepository.getItemNames()).thenReturn(expectedList);

        ItemService.getItemNames();

        Mockito.calls(2);
    }
}
