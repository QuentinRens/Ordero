package com.rensq.ordero.api.customer;

import com.rensq.ordero.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/customer_registration")
public class CustomerRegistrationController {
    private CustomerService customerService;
    private CustomerMapper customerMapper;

    @Inject
    public CustomerRegistrationController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto registerCustomer (@RequestBody CustomerDto customerDto){
        return customerMapper.toDto(customerService.createCustomer(customerMapper.toDomain(customerDto)));
    }
}
