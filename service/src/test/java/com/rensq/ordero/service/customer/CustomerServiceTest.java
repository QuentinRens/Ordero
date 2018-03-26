package com.rensq.ordero.service.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;
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
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress().build())
                .build();

        Customer expectedCustomer = Customer.CustomerBuilder.customer()
                .withID(UUID.randomUUID())
                .withFirstName(providedCustomer.getFirstName())
                .withLastName(providedCustomer.getLastName())
                .withEmail(providedCustomer.getEmail())
                .withPhoneNumber(providedCustomer.getPhoneNumber())
                .withCustomerAddress(providedCustomer.getCustomerAddress())
                .build();

        Mockito.when(CustomerRepository.storeCustomer(providedCustomer)).thenReturn(expectedCustomer);

        Customer actualCustomer = CustomerService.createCustomer(providedCustomer);

        Assertions.assertThat(actualCustomer).isEqualToComparingFieldByField(expectedCustomer);
    }

    @Test
    public void getCustomer_HappyPath() {
        UUID givenID = UUID.randomUUID();
        Customer expectedCustomer = Customer.CustomerBuilder.customer().withID(givenID).build();
        Mockito.when(CustomerRepository.getCustomer(givenID)).thenReturn(expectedCustomer);

        CustomerService.getCustomer(givenID);

        Mockito.calls(2);
    }
}
