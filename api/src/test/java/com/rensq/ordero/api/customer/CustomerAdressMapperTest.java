package com.rensq.ordero.api.customer;

import com.rensq.ordero.domain.customer.CustomerAddress;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;


public class CustomerAdressMapperTest {
    private CustomerAddressMapper customerAddressMapper;

    @Before
    public void instantiateMapper(){customerAddressMapper = new CustomerAddressMapper();}

    @Test
    public void toDto_givenCustomerAddress_thenMapAllFieldsToCustomerAddressDto(){
        CustomerAddress address = CustomerAddress.CustomerAddressBuilder.customerAddress()
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120")
                .build();

        CustomerAddressDto actualAdressDto = customerAddressMapper.toDto(address);

        Assertions.assertThat(actualAdressDto).isEqualToComparingFieldByField(address);
    }

    @Test
    public void toDto_givenCustomerAddressDto_thenMapAllFieldsToCustomerAddress(){
        CustomerAddressDto addressDto = CustomerAddressDto.customerAddressDto()
                .withStreetName("Saint-Feuillen")
                .withStreetNumber("3")
                .withCity("Charleroi")
                .withPostalCode("6120");

        CustomerAddress actualAdress = customerAddressMapper.toDomain(addressDto);

        Assertions.assertThat(actualAdress).isEqualToComparingFieldByField(addressDto);
    }


}
