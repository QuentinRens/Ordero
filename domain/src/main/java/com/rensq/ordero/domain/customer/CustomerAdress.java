package com.rensq.ordero.domain.customer;

public class CustomerAdress {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    private CustomerAdress(){}

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

    public static class CustomerAdressBuilder{
        private String streetName;
        private String streetNumber;
        private String postalCode;
        private String city;

        private CustomerAdressBuilder(){}

        public static CustomerAdressBuilder customerAdress(){
            return new CustomerAdressBuilder();
        }

        public CustomerAdress build(){
            CustomerAdress customerAdress = new CustomerAdress();
            customerAdress.setCity(city);
            customerAdress.setPostalCode(postalCode);
            customerAdress.setStreetName(streetName);
            customerAdress.setStreetNumber(streetNumber);
            return  customerAdress;
        }

        public CustomerAdressBuilder withStreetName(String streetName){
            this.streetName = streetName;
            return this;
        }

        public CustomerAdressBuilder withStreetNumber(String streetNumber){
            this.streetNumber = streetNumber;
            return this;
        }

        public CustomerAdressBuilder withPostalCode(String postalCode){
            this.postalCode = postalCode;
            return this;
        }

        public CustomerAdressBuilder withCity(String city) {
            this.city = city;
            return this;
        }
    }
}
