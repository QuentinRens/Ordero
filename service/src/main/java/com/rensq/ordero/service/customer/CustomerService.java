package com.rensq.ordero.service.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Inject
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer (Customer providedCustomer){
        return customerRepository.storeCustomer(providedCustomer);
    }
}
