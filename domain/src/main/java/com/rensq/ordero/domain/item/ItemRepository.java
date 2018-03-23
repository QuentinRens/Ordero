package com.rensq.ordero.domain.item;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class ItemRepository {
    private Map<Integer, Item> items;
    private static int databaseIndex = 0;

    public ItemRepository(){
        this.items = new HashMap<>();
    }

    public Item getItem (int id){
        return items.get(id);
    }

    public Item getItemByName (String itemName){
        return items.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public List<Item> getItems(){
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public List <String> getItemNames(){
        List <String> itemNames = items.values().stream().map(item -> item.getName()).collect(Collectors.toList());
        return Collections.unmodifiableList(itemNames);
    }

    public Item storeItem(Item item){
        item.setId(++databaseIndex);
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem (Item updatedItem){
        items.put(updatedItem.getId(), updatedItem);
        return updatedItem;
    }

    public void clear(){
        items.clear();
        databaseIndex = 0;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
