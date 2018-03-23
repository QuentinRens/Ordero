package com.rensq.ordero.api.customer;

import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRegistrationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Inject
    private CustomerRepository customerRepository;


    @Test
    public void registerCustomer() {
        CustomerDto customerDto = new TestRestTemplate()
                .postForObject(String.format("http://localhost:%s/%s", port, "customer_registration"),
                        CustomerDto.customerDto()
                                .withFirstName("Jean")
                                .withLastName("Marc")
                                .withEmail("jeanmarc@hotmail.com")
                                .withPhoneNumber("071/21/54/87")
                                .withCity("Charleroi")
                                .withPostalCode("6120")
                                .withStreetName("Saint-Jean")
                                .withStreetNumber("42")
                        ,
                        CustomerDto.class);

        Assertions.assertThat(customerDto.getId()).isNotNull();
        Assertions.assertThat(customerDto.getFirstName()).isEqualTo("Jean");
        Assertions.assertThat(customerDto.getLastName()).isEqualTo("Marc");
        Assertions.assertThat(customerDto.getEmail()).isEqualTo("jeanmarc@hotmail.com");
        Assertions.assertThat(customerDto.getPhoneNumber()).isEqualTo("071/21/54/87");
        Assertions.assertThat(customerDto.getPostalCode()).isEqualTo("6120");
        Assertions.assertThat(customerDto.getStreetName()).isEqualTo("Saint-Jean");
        Assertions.assertThat(customerDto.getCity()).isEqualTo("Charleroi");
        Assertions.assertThat(customerDto.getStreetNumber()).isEqualTo("42");
    }
}
