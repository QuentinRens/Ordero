package com.rensq.ordero.api.item;

import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.service.item.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/Item")
public class ItemManagementController {
    private ItemService itemService;
    private ItemMapper itemMapper;

    @Inject
    public ItemManagementController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.CREATED)
    public ItemDto addItem(@RequestBody ItemDto itemDto) {
       return itemMapper.toDto(itemService.createItem(itemMapper.toDomain(itemDto)));
    }


    @PutMapping (path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public ItemDto updateItem (@PathVariable String id, @RequestBody ItemDto itemDto){
        return itemMapper.toDto (itemService.updateItem(id, itemMapper.toDomain(itemDto)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus (HttpStatus.OK)
    public List<ItemDto> getItemByStockResupplyUrgency(){
        List <Item> items = itemService.getItemByStockResupplyUrgency();
        return items.stream()
                .map(item -> itemMapper.toDto(item))
                .collect(Collectors.toList());
    }

}
