package com.rensq.ordero.api.customer;


import com.rensq.ordero.api.OrderoRunner;
import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;
import com.rensq.ordero.domain.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderoRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerInfoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Inject
    private CustomerRepository customerRepository;

    @Before
    public void clearRepository(){
        customerRepository.clear();
    }

    @Test
    public void getAllCustomers(){
        customerRepository.storeCustomer(Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().withCity("Charleroi")
                        .withPostalCode("6120")
                        .withStreetName("Saint-Jean")
                        .withStreetNumber("42").build())
                .build());

        customerRepository.storeCustomer(Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().withCity("Charleroi")
                        .withPostalCode("6120")
                        .withStreetName("Saint-Jean")
                        .withStreetNumber("42").build())
                .build());

        customerRepository.storeCustomer(Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().withCity("Charleroi")
                        .withPostalCode("6120")
                        .withStreetName("Saint-Jean")
                        .withStreetNumber("42").build())
                .build());

        customerRepository.storeCustomer(Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().withCity("Charleroi")
                        .withPostalCode("6120")
                        .withStreetName("Saint-Jean")
                        .withStreetNumber("42").build())
                .build());


        CustomerDto[] customerDtos = new TestRestTemplate().getForObject(String.format("http://localhost:%s/%s", port, "customers"), CustomerDto[].class);

        Assertions.assertThat(customerDtos.length).isEqualTo(4);
    }

    @Test
    public void getCustomerInfo(){
        Customer customer = customerRepository.storeCustomer(Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().withCity("Charleroi")
                        .withPostalCode("6120")
                        .withStreetName("Saint-Jean")
                        .withStreetNumber("42").build())
                .build());

        CustomerDto customerDto = new TestRestTemplate().getForObject(String.format("http://localhost:%s/%s/%s", port, "customers", customer.getId().toString()), CustomerDto.class);

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
