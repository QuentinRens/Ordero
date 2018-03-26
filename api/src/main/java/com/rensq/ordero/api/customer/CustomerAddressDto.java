package com.rensq.ordero.api.customer;

public class CustomerAddressDto {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    private CustomerAddressDto(){}

    public static CustomerAddressDto customerAddressDto(){
        return new CustomerAddressDto();
    }

    public CustomerAddressDto withStreetName(String streetName){
        this.streetName = streetName;
        return this;
    }

    public CustomerAddressDto withStreetNumber(String streetNumber){
        this.streetNumber = streetNumber;
        return this;
    }

    public CustomerAddressDto withPostalCode(String postalCode){
        this.postalCode = postalCode;
        return this;
    }

    public CustomerAddressDto withCity(String city) {
        this.city = city;
        return this;
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
