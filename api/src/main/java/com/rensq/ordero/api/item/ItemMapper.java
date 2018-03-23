package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.Item;

import javax.inject.Named;

@Named
public class ItemMapper {

    ItemDto toDto (Item item){
        return ItemDto.itemDto()
                .withID(item.getId())
                .withName(item.getName())
                .withDescription(item.getDescription())
                .withPrice(item.getPrice())
                .withAmount(item.getAmount());
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
