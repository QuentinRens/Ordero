package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.ItemGroup;

import javax.inject.Named;
import java.time.format.DateTimeFormatter;

@Named
public class ItemGroupMapper {

    public ItemGroup toDomain (ItemGroupDto itemGroupDto){
        return ItemGroup.ItemGroupBuilder.itemGroup()
                .withName(itemGroupDto.getName())
                .withDescription(itemGroupDto.getDescription())
                .withAmount(itemGroupDto.getAmount())
                .build();
    }

    public ItemGroupDto toDto (ItemGroup itemGroup){
        return ItemGroupDto.itemGroupDto()
                .withName(itemGroup.getName())
                .withDescription(itemGroup.getDescription())
                .withAmount(itemGroup.getAmount())
                .withPrice(itemGroup.getPrice().intValue())
                .withShippingDate(itemGroup.getShippingDate().format(DateTimeFormatter.ISO_DATE));
    }
}
