package com.pizzashop.data.models;

public class Address {
    private int id;
    private String street;
    private int number;
    private String sufix;
    private String zipCode;
    private String city;

    public Address() {
    }

    public Address(String street, int number, String sufix, String zipCode, String city) {
        this.street = street;
        this.number = number;
        this.sufix = sufix;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address(int id, String street, int number, String sufix, String zipCode, String city) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.sufix = sufix;
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSufix() {
        return sufix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + id;
        result = prime * result + number;
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((sufix == null) ? 0 : sufix.hashCode());
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (id != other.id)
            return false;
        if (number != other.number)
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (sufix == null) {
            if (other.sufix != null)
                return false;
        } else if (!sufix.equals(other.sufix))
            return false;
        if (zipCode == null) {
            if (other.zipCode != null)
                return false;
        } else if (!zipCode.equals(other.zipCode))
            return false;
        return true;
    }

    

}