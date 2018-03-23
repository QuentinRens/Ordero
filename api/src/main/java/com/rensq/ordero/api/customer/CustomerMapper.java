package com.rensq.ordero.api.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerAdress;

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
                .withCity(customer.getCustomerAdress().getCity())
                .withPostalCode(customer.getCustomerAdress().getPostalCode())
                .withStreetNumber(customer.getCustomerAdress().getStreetNumber())
                .withStreetName(customer.getCustomerAdress().getStreetName());
    }

    Customer toDomain (CustomerDto customerDto){
        return Customer.CustomerBuilder.customer()
                .withID(customerDto.getId())
                .withFirstName(customerDto.getFirstName())
                .withLastName(customerDto.getLastName())
                .withEmail(customerDto.getEmail())
                .withPhoneNumber(customerDto.getPhoneNumber())
                .withCustomerAdress(CustomerAdress.CustomerAdressBuilder.customerAdress()
                        .withCity(customerDto.getCity())
                        .withPostalCode(customerDto.getPostalCode())
                        .withStreetNumber(customerDto.getStreetNumber())
                        .withStreetName(customerDto.getStreetName())
                        .build())
                .build();
    }
}
