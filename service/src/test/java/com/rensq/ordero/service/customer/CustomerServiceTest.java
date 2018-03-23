package com.rensq.ordero.service.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAdress;
import com.rensq.ordero.domain.customer.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository CustomerRepository;

    @InjectMocks
    private CustomerService CustomerService;

    @Test
    public void createCustomer_HappyPath() {
        Customer providedCustomer = Customer.CustomerBuilder.customer()
                .withFirstName("Jean")
                .withLastName("Marc")
                .withEmail("jeanmarc@hotmail.com")
                .withPhoneNumber("071/21/98/99")
                .withCustomerAdress(CustomerAdress.CustomerAdressBuilder.customerAdress().build())
                .build();

        Customer expectedCustomer = Customer.CustomerBuilder.customer()
                .withID(UUID.randomUUID())
                .withFirstName(providedCustomer.getFirstName())
                .withLastName(providedCustomer.getLastName())
                .withEmail(providedCustomer.getEmail())
                .withPhoneNumber(providedCustomer.getPhoneNumber())
                .withCustomerAdress(providedCustomer.getCustomerAdress())
                .build();

        Mockito.when(CustomerRepository.storeCustomer(providedCustomer)).thenReturn(expectedCustomer);

        Customer actualCustomer = CustomerService.createCustomer(providedCustomer);

        Assertions.assertThat(actualCustomer).isEqualToComparingFieldByField(expectedCustomer);
    }
}