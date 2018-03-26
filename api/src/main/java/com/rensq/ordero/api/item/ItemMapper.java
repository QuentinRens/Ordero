package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.StockResupplyUrgency;

import javax.inject.Named;
import java.time.format.DateTimeFormatter;

@Named
public class ItemMapper {

    ItemDto toDto (Item item){
        return ItemDto.itemDto()
                .withID(item.getId().toString())
                .withName(item.getName())
                .withDescription(item.getDescription())
                .withPrice(item.getPrice())
                .withAmount(item.getAmount())
                .withLastOrdered(item.getLastOrdered() == null? null : item.getLastOrdered().format(DateTimeFormatter.ISO_DATE))
                .withStockResupplyUrgency(item.getStockResupplyUrgency());
    }

    Item toDomain (ItemDto itemDto){
        return Item.ItemBuilder.item()
                .withName(itemDto.getName())
                .withDescription(itemDto.getDescription())
                .withPrice(itemDto.getPrice())
                .withAmount(itemDto.getAmount())
                .build();
    }
}
