package com.rensq.ordero.domain.customer;

import javax.inject.Named;
import java.util.*;

@Named
public class CustomerRepository {
    private Map<UUID, Customer> customers;

    public CustomerRepository(){
        customers = new HashMap<>();
    }

    public Customer storeCustomer(Customer customer){
        customer.setId(UUID.randomUUID());
        customers.put(customer.getId(), customer);
        return customer;
    }

    public Customer getCustomer (UUID id){
        return customers.get(id);
    }

    public List<Customer> getAllCustomers(){
        return Collections.unmodifiableList(new ArrayList<>(customers.values()));
    }

    public void clear(){
        customers.clear();
    }
}
