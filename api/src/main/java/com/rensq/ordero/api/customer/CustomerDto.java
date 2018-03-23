package com.rensq.ordero.api.customer;

import java.util.UUID;

public class CustomerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    private CustomerDto(){}

    public static CustomerDto customerDto(){
        return new CustomerDto();
    }

    public CustomerDto withID (UUID id){
        this.id = id;
        return this;
    }

    public CustomerDto withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public CustomerDto withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public CustomerDto withEmail(String email){
        this.email = email;
        return this;
    }

    public CustomerDto withPhoneNumber (String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerDto withStreetName(String streetName){
        this.streetName = streetName;
        return this;
    }

    public CustomerDto withStreetNumber(String streetNumber){
        this.streetNumber = streetNumber;
        return this;
    }

    public CustomerDto withPostalCode(String postalCode){
        this.postalCode = postalCode;
        return this;
    }

    public CustomerDto withCity(String city) {
        this.city = city;
        return this;
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

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
