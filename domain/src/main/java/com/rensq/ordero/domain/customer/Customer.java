package com.rensq.ordero.domain.customer;

import java.util.UUID;

public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private CustomerAddress customerAdress;

    private Customer(){}

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerAddress(CustomerAddress customerAdress) {
        this.customerAdress = customerAdress;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAdress;
    }

    public static class CustomerBuilder{
        private UUID id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private CustomerAddress customerAddress;

        private CustomerBuilder(){}

        public static CustomerBuilder customer(){
            return new CustomerBuilder();
        }

        public Customer build(){
            Customer customer = new Customer();
            customer.setId(id);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setCustomerAddress(customerAddress);
            return customer;
        }

        public CustomerBuilder withID (UUID id){
            this.id = id;
            return this;
        }

        public CustomerBuilder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public CustomerBuilder withPhoneNumber (String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public  CustomerBuilder withCustomerAddress (CustomerAddress customerAdress){
            this.customerAddress = customerAdress;
            return this;
        }
    }
}
