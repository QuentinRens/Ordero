package com.rensq.ordero.api.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerMapperTest {
    private CustomerMapper customerMapper;

    @Before
    public void instantiateMapper() {
        customerMapper = new CustomerMapper();
    }

    @Test
    public void toDto_givenCustomer_thenMapAllFieldsToCustomerDto() {
        Customer customer = Customer.CustomerBuilder.customer()
                .withID(UUID.randomUUID())
                .withFirstName("Quentin")
                .withLastName("Rens")
                .withEmail("rensquentin@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress()
                        .withStreetName("Saint-Feuillen")
                        .withStreetNumber("3")
                        .withCity("Charleroi")
                        .withPostalCode("6120")
                        .build())
                .build();

        CustomerDto customerDto = customerMapper.toDto(customer);

        assertThat(customerDto).isEqualToComparingOnlyGivenFields(customer, "id", "firstName", "lastName", "email", "phoneNumber");
        assertThat(customerDto).isEqualToComparingOnlyGivenFields(customer.getCustomerAddress(), "streetName", "streetNumber", "postalCode", "city");
    }

    @Test
    public void toDomain_givenCustomerDto_thenMapAllFieldsToCustomer() {
        CustomerDto customerDto = CustomerDto.customerDto()
                .withFirstName("Quentin")
                .withLastName("Rens")
                .withEmail("rensquentin@hotmail.com")
                .withPhoneNumber("071/21/54/87")
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120");

        Customer customer = customerMapper.toDomain(customerDto);

        assertThat(customer).isEqualToComparingOnlyGivenFields(customerDto, "firstName", "lastName", "email", "phoneNumber");
        assertThat(customer.getCustomerAddress()).isEqualToComparingOnlyGivenFields(customerDto, "streetName", "streetNumber", "postalCode", "city");
    }
}
