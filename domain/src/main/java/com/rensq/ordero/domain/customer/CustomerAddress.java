package com.rensq.ordero.domain.customer;

public class CustomerAddress {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    private CustomerAddress(){}

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
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

    public static class CustomerAddressBuilder{
        private String streetName;
        private String streetNumber;
        private String postalCode;
        private String city;

        private CustomerAddressBuilder(){}

        public static CustomerAddressBuilder customerAddress(){
            return new CustomerAddressBuilder();
        }

        public CustomerAddress build(){
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setCity(city);
            customerAddress.setPostalCode(postalCode);
            customerAddress.setStreetName(streetName);
            customerAddress.setStreetNumber(streetNumber);
            return  customerAddress;
        }

        public CustomerAddressBuilder withStreetName(String streetName){
            this.streetName = streetName;
            return this;
        }

        public CustomerAddressBuilder withStreetNumber(String streetNumber){
            this.streetNumber = streetNumber;
            return this;
        }

        public CustomerAddressBuilder withPostalCode(String postalCode){
            this.postalCode = postalCode;
            return this;
        }

        public CustomerAddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }
    }
}
