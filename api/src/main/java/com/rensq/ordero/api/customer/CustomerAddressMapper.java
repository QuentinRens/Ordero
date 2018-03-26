package com.rensq.ordero.api.customer;

import com.rensq.ordero.domain.customer.CustomerAddress;

import javax.inject.Named;

@Named
public class CustomerAddressMapper {

    public CustomerAddressDto toDto (CustomerAddress customerAddress){
        return CustomerAddressDto.customerAddressDto()
                .withCity(customerAddress.getCity())
                .withPostalCode(customerAddress.getPostalCode())
                .withStreetName(customerAddress.getStreetName())
                .withStreetNumber(customerAddress.getStreetNumber());
    }

    public CustomerAddress toDomain (CustomerAddressDto customerAddressDto){
        return CustomerAddress.CustomerAddressBuilder.customerAddress()
                .withCity(customerAddressDto.getCity())
                .withPostalCode(customerAddressDto.getPostalCode())
                .withStreetName(customerAddressDto.getStreetName())
                .withStreetNumber(customerAddressDto.getStreetNumber())
                .build();
    }
}
