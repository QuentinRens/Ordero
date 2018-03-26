package com.rensq.ordero.service.item;


import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.domain.item.StockResupplyUrgency;
import com.rensq.ordero.service.exceptions.EmptyFieldException;
import com.rensq.ordero.service.exceptions.EmptyResourceException;
import com.rensq.ordero.service.exceptions.ItemCreationException;
import com.rensq.ordero.service.exceptions.UnknownResourceException;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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

    public List <Item> getItemByStockResupplyUrgency(){
        return itemRepository.getItemByStockResupplyUrgency();
    }

    public Item createItem(Item providedItem) {
        assertItemNameIsFree(providedItem);
        assertPriceIsNotNull(providedItem);
        assertAmountIsNotNull(providedItem);
        assertDescriptionIsNotNull(providedItem);
        setStockResupplyUrgency(providedItem);
        return itemRepository.storeItem(providedItem);
    }

    private void assertDescriptionIsNotNull(Item providedItem) {
        if (providedItem.getDescription() == null){
            throw new EmptyFieldException();
        }
    }

    public Item updateItem(String itemId, Item updatedItem) {
        UUID itemIDasUUID = UUID.fromString(itemId);
        assertItemIsPresent(itemRepository.getItem(itemIDasUUID));
        fillForEmptyName(itemIDasUUID, updatedItem);
        fillForEmptyDescription(itemIDasUUID, updatedItem);
        fillForEmptyPrice(itemIDasUUID, updatedItem);
        fillForEmptyAmount(itemIDasUUID, updatedItem);
        updatedItem.setLastOrdered(itemRepository.getItem(itemIDasUUID).getLastOrdered());
        updatedItem.setId(itemIDasUUID);
        setStockResupplyUrgency(updatedItem);
        return itemRepository.updateItem(updatedItem);
    }

    private void assertAmountIsNotNull(Item providedItem) {
        if (providedItem.getAmount() == null){
            throw new EmptyFieldException();
        }
    }

    private void setStockResupplyUrgency(Item providedItem) {
        if(providedItem.getAmount() < 3){
            providedItem.setStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW);
        } else if (providedItem.getAmount() < 10){
            if (providedItem.getAmount() < 5 && providedItem.getLastOrdered() != null && providedItem.getLastOrdered().plusDays(8).isAfter(LocalDate.now())){
                providedItem.setStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW);
            }else{
                providedItem.setStockResupplyUrgency(StockResupplyUrgency.STOCK_MEDIUM);
            }
        } else{
            providedItem.setStockResupplyUrgency(StockResupplyUrgency.STOCK_HIGH);
        }
    }

    private void assertPriceIsNotNull(Item providedItem) {
        if (providedItem.getPrice() == null){
            throw new IllegalArgumentException();
        }
    }


    private void fillForEmptyAmount(UUID itemId, Item updatedItem) {
        if (updatedItem.getName() == null) {
            updatedItem.setAmount(itemRepository.getItem(itemId).getAmount());
        }
    }

    private void fillForEmptyPrice(UUID itemId, Item updatedItem) {
        if (updatedItem.getPrice() == null) {
            updatedItem.setPrice(itemRepository.getItem(itemId).getPrice());
        }
    }

    private void fillForEmptyDescription(UUID itemId, Item updatedItem) {
        if (updatedItem.getDescription() == null) {
            updatedItem.setDescription(itemRepository.getItem(itemId).getDescription());
        }
    }

    private void fillForEmptyName(UUID itemId, Item updatedItem) {
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
