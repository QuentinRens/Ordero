package com.rensq.ordero.domain.item;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class ItemRepository {
    private Map<Integer, Item> items;
    private static int databaseIndex = 0;

    public ItemRepository(){
        this.items = new HashMap<>();
    }

    public Item storeItem(Item item){
        item.setId(++databaseIndex);
        items.put(item.getId(), item);
        return item;
    }

    public void clear(){
        items.clear();
    }
}
