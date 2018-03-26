package com.rensq.ordero.domain.item;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class ItemRepository {
    private Map<UUID, Item> items;

    public ItemRepository(){
        this.items = new HashMap<>();
    }

    public Item getItem (UUID id){
        return items.get(id);
    }

    public Item getItemByName (String itemName){
        return items.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public List <Item> getItemByStockResupplyUrgency(){
        return items.values().stream()
                .filter(item -> item.getStockResupplyUrgency() == StockResupplyUrgency.STOCK_LOW || item.getStockResupplyUrgency() == StockResupplyUrgency.STOCK_MEDIUM).sorted()
                .collect(Collectors.toList());
    }

    public List<Item> getItems(){
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public List <String> getItemNames(){
        List <String> itemNames = items.values().stream().map(item -> item.getName()).collect(Collectors.toList());
        return Collections.unmodifiableList(itemNames);
    }

    public Item storeItem(Item item){
        item.setId(UUID.randomUUID());
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem (Item updatedItem){
        items.put(updatedItem.getId(), updatedItem);
        return updatedItem;
    }

    public void clear(){
        items.clear();
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
