package com.rensq.ordero.service.item;


import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ItemService {
    private final ItemRepository itemRepository;

    @Inject
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem (Item providedItem){
        return itemRepository.storeItem(providedItem);
    }
}
