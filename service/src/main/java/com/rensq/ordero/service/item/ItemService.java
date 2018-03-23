package com.rensq.ordero.service.item;


import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.service.exceptions.EmptyResourceException;
import com.rensq.ordero.service.exceptions.ItemCreationException;
import com.rensq.ordero.service.exceptions.UnknownResourceException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class ItemService {
    private final ItemRepository itemRepository;

    @Inject
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemByName (String itemName){
        assertItemNameIsPresent(itemName);
        return itemRepository.getItemByName(itemName);
    }

    public List<String> getItemNames(){
        assertListHasNames();
        return itemRepository.getItemNames();
    }

    public Item createItem(Item providedItem) {
        assertItemNameIsFree(providedItem);
        assertPriceIsNotNull(providedItem);
        return itemRepository.storeItem(providedItem);
    }

    private void assertPriceIsNotNull(Item providedItem) {
        if (providedItem.getPrice() == null){
            throw new IllegalArgumentException();
        }
    }

    public Item updateItem(int itemId, Item updatedItem) {
        assertItemIsPresent(itemRepository.getItem(itemId));
        fillForEmptyName(itemId, updatedItem);
        fillForEmptyDescription(itemId, updatedItem);
        fillForEmptyPrice(itemId, updatedItem);
        fillForEmptyAmount(itemId, updatedItem);
        updatedItem.setId(itemId);
        return itemRepository.updateItem(updatedItem);
    }

    private void fillForEmptyAmount(int itemId, Item updatedItem) {
        if (updatedItem.getName() == null) {
            updatedItem.setAmount(itemRepository.getItem(itemId).getAmount());
        }
    }

    private void fillForEmptyPrice(int itemId, Item updatedItem) {
        if (updatedItem.getPrice() == null) {
            updatedItem.setPrice(itemRepository.getItem(itemId).getPrice());
        }
    }

    private void fillForEmptyDescription(int itemId, Item updatedItem) {
        if (updatedItem.getDescription() == null) {
            updatedItem.setDescription(itemRepository.getItem(itemId).getDescription());
        }
    }

    private void fillForEmptyName(int itemId, Item updatedItem) {
        if (updatedItem.getName() == null) {
            updatedItem.setName(itemRepository.getItem(itemId).getName());
        }
    }

    private void assertItemIsPresent(Item queriedItemById) {
        if (queriedItemById == null) {
            throw new UnknownResourceException("ID", Item.class.getSimpleName());
        }
    }

    private void assertListHasNames() {
        if (itemRepository.getItemNames().isEmpty()){
            throw new EmptyResourceException();
        }
    }

    private void assertItemNameIsFree(Item providedItem) {
        if (itemRepository.getItems().stream()
                .map(item -> item.getName())
                .collect(Collectors.toList())
                .contains(providedItem.getName())){
            throw new ItemCreationException();
        }
    }

    private void assertItemNameIsPresent(String itemName) {
        if (itemRepository.getItems().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null) == null){
            throw new UnknownResourceException("name", Item.class.getSimpleName());
        }
    }


}
