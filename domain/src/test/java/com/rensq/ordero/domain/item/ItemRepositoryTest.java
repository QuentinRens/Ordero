package com.rensq.ordero.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class ItemRepositoryTest {
    private ItemRepository itemRepository;

    @Before
    public void instantiateRepository() {
        itemRepository = new ItemRepository();
    }


    @Test
    public void getItemByName_GivenAName_ShouldReturnItemWithThatName(){
        Item expectedItem = itemRepository.storeItem(Item.ItemBuilder.item().withName("Marcus").build());
        Item otherItem = itemRepository.storeItem(Item.ItemBuilder.item().withName("Marcos").build());

        Item actualItem = itemRepository.getItemByName("Marcus");

        Assertions.assertThat(actualItem.getName()).isEqualTo("Marcus");
    }

    @Test
    public void getItemNames_Given2ItemsInRepository_ShouldReturnAListWithBothOfTheirNames(){
        Item expectedItem = itemRepository.storeItem(Item.ItemBuilder.item().withName("Marcus").build());
        Item otherItem = itemRepository.storeItem(Item.ItemBuilder.item().withName("Marcos").build());

        List<String> actualItems = itemRepository.getItemNames();

        Assertions.assertThat(actualItems).containsExactlyInAnyOrder("Marcus", "Marcos");
    }

    @Test
    public void getItemByStockResupplyUrgency_GivenItemsWithStockSupplyUrgency_ShouldReturnSortedByMostUrgentList(){
        Item item1 = itemRepository.storeItem(Item.ItemBuilder.item().withName("Banana").withLastOrdered(LocalDate.now()).withAmount(7).withStockResupplyUrgency(StockResupplyUrgency.STOCK_MEDIUM).build());
        Item item2 = itemRepository.storeItem(Item.ItemBuilder.item().withName("Toy").withAmount(2).withStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW).build());
        Item item3 = itemRepository.storeItem(Item.ItemBuilder.item().withName("Bike").withLastOrdered(LocalDate.now()).withAmount(3).withStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW).build());
        Item item4 = itemRepository.storeItem(Item.ItemBuilder.item().withName("Orange").withLastOrdered(LocalDate.now().minusDays(4)).withAmount(8).withStockResupplyUrgency(StockResupplyUrgency.STOCK_MEDIUM).build());
        Item item5 = itemRepository.storeItem(Item.ItemBuilder.item().withName("House").withLastOrdered(LocalDate.now().minusDays(4)).withAmount(7).withStockResupplyUrgency(StockResupplyUrgency.STOCK_MEDIUM).build());

        List<Item> actualItems = itemRepository.getItemByStockResupplyUrgency();

        Assertions.assertThat(actualItems).containsExactly(item3, item2, item5, item4, item1);
    }
}
