package com.rensq.ordero.api.customer;

import com.rensq.ordero.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/customers")
public class CustomerInfoController {
    private CustomerService customerService;
    private CustomerMapper customerMapper;

    @Inject
    public CustomerInfoController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping (produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers(){
        return customerService.getAllCustomers().stream()
                .map(customer -> customerMapper.toDto(customer))
                .collect(Collectors.toList());
    }

    @GetMapping (path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerInfo(@PathVariable("id") String customerId){
        return customerMapper.toDto(customerService.getCustomer(UUID.fromString(customerId)));
    }
}
