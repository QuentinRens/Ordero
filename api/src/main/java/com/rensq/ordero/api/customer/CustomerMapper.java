package com.rensq.ordero.api.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAddress;

import javax.inject.Named;

@Named
public class CustomerMapper {

    CustomerDto toDto (Customer customer){
        return CustomerDto.customerDto()
                .withID(customer.getId())
                .withFirstName(customer.getFirstName())
                .withLastName(customer.getLastName())
                .withEmail(customer.getEmail())
                .withPhoneNumber(customer.getPhoneNumber())
                .withCity(customer.getCustomerAddress().getCity())
                .withPostalCode(customer.getCustomerAddress().getPostalCode())
                .withStreetNumber(customer.getCustomerAddress().getStreetNumber())
                .withStreetName(customer.getCustomerAddress().getStreetName());
    }

    Customer toDomain (CustomerDto customerDto){
        return Customer.CustomerBuilder.customer()
                .withFirstName(customerDto.getFirstName())
                .withLastName(customerDto.getLastName())
                .withEmail(customerDto.getEmail())
                .withPhoneNumber(customerDto.getPhoneNumber())
                .withCustomerAddress(CustomerAddress.CustomerAddressBuilder.customerAddress()
                        .withCity(customerDto.getCity())
                        .withPostalCode(customerDto.getPostalCode())
                        .withStreetNumber(customerDto.getStreetNumber())
                        .withStreetName(customerDto.getStreetName())
                        .build())
                .build();
    }
}
