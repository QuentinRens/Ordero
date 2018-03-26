package com.rensq.ordero.api.item;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
import com.rensq.ordero.domain.item.StockResupplyUrgency;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemManagementControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Inject
    private ItemRepository itemRepository;

    @Before
    public void initRepository() {
        itemRepository.clear();
    }

    @Test
    public void addItem() {
        ItemDto itemDto = new TestRestTemplate()
                .postForObject(String.format("http://localhost:%s/%s", port, "Item"),
                        ItemDto.itemDto()
                                .withName("Toy")
                                .withDescription("A big toy")
                                .withPrice(new BigDecimal(13))
                                .withAmount(10)
                        ,
                        ItemDto.class);

        Assertions.assertThat(itemDto.getId()).isNotNull();
        Assertions.assertThat(itemDto.getName()).isEqualTo("Toy");
        Assertions.assertThat(itemDto.getDescription()).isEqualTo("A big toy");
        Assertions.assertThat(itemDto.getPrice()).isEqualByComparingTo(new BigDecimal(13));
        Assertions.assertThat(itemDto.getAmount()).isEqualTo(10);
        Assertions.assertThat(itemDto.getLastOrdered()).isEqualTo(null);
        Assertions.assertThat(itemDto.getStockResupplyUrgency()).isEqualTo(StockResupplyUrgency.STOCK_HIGH);
    }

    @Test
    public void updateItem() {
        Item item = itemRepository.storeItem(Item.ItemBuilder.item()
                .withName("Toytoytoy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(15))
                .withAmount(10)
                .withLastOrdered(LocalDate.now())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_HIGH)
                .build());

        new TestRestTemplate().put(String.format("http://localhost:%s/%s/%s", port, "Item", item.getId().toString()),
                        ItemDto.itemDto()
                                .withName("Toy")
                                .withPrice(new BigDecimal(13))
                                .withAmount(4)
                        , ItemDto.class);

        Assertions.assertThat(itemRepository.getItem(item.getId())).isEqualToIgnoringGivenFields(ItemDto.itemDto()
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(4), "id", "lastOrdered", "stockResupplyUrgency");

        Assertions.assertThat(itemRepository.getItem(item.getId()).getStockResupplyUrgency()).isEqualTo(StockResupplyUrgency.STOCK_LOW);
    }

    @Test
    public void getItemByStockResupplyUrgency(){
        Item item1 = itemRepository.storeItem(Item.ItemBuilder.item()
                .withName("Toytoytoy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(15))
                .withAmount(10)
                .withLastOrdered(LocalDate.now())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_HIGH)
                .build());

        Item item2 = itemRepository.storeItem(Item.ItemBuilder.item()
                .withName("Toytoytoy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(15))
                .withAmount(10)
                .withLastOrdered(LocalDate.now())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_MEDIUM)
                .build());

        Item item3 = itemRepository.storeItem(Item.ItemBuilder.item()
                .withName("Tututu")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(15))
                .withAmount(10)
                .withLastOrdered(LocalDate.now())
                .withStockResupplyUrgency(StockResupplyUrgency.STOCK_LOW)
                .build());

        ItemDto[] itemDto = new TestRestTemplate()
                .getForObject((String.format("http://localhost:%s/%s", port, "Item")), ItemDto[].class);

        Assertions.assertThat(itemDto).hasSize(2);
        Assertions.assertThat(itemDto[0].getName()).isEqualTo("Tututu");
        Assertions.assertThat(itemDto[1].getName()).isEqualTo("Toytoytoy");

    }
}
