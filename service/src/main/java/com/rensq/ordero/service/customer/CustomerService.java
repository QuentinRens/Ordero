package com.rensq.ordero.service.customer;

import com.rensq.ordero.domain.customer.Customer;
import com.rensq.ordero.domain.customer.CustomerRepository;
import com.rensq.ordero.service.exceptions.UnknownResourceException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

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

    public Customer getCustomer (UUID customerID){
        assertCustomerExist(customerID);
        return customerRepository.getCustomer(customerID);
    }

    private void assertCustomerExist(UUID customerID) {
        if (customerRepository.getCustomer(customerID) == null){
            throw new UnknownResourceException("ID", Customer.class.getSimpleName());
        }
    }
}
