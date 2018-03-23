package com.rensq.ordero.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ItemRepositoryTest {
    private ItemRepository itemRepository;

    @Before
    public void instantiateMapper() {
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

        Assertions.assertThat(actualItems).containsExactly("Marcus", "Marcos");
    }
}
