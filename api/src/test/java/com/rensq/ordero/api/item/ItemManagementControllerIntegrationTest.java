package com.rensq.ordero.api.item;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.item.Item;
import com.rensq.ordero.domain.item.ItemRepository;
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

        Assertions.assertThat(itemDto.getId()).isEqualTo(1);
        Assertions.assertThat(itemDto.getName()).isEqualTo("Toy");
        Assertions.assertThat(itemDto.getDescription()).isEqualTo("A big toy");
        Assertions.assertThat(itemDto.getPrice()).isEqualByComparingTo(new BigDecimal(13));
        Assertions.assertThat(itemDto.getAmount()).isEqualTo(10);
    }

    @Test
    public void updateItem() {
        Item item = itemRepository.storeItem(Item.ItemBuilder.item()
                .withName("Toytoytoy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(15))
                .withAmount(10)
                .build());

        new TestRestTemplate().put(String.format("http://localhost:%s/%s/%s", port, "Item", 1),
                        ItemDto.itemDto()
                                .withName("Toy")
                                .withPrice(new BigDecimal(13))
                                .withAmount(10)
                        , ItemDto.class);

        Assertions.assertThat(itemRepository.getItem(1)).isEqualToComparingFieldByField(ItemDto.itemDto()
                .withID(1)
                .withName("Toy")
                .withDescription("A big toy")
                .withPrice(new BigDecimal(13))
                .withAmount(10));
    }
}
