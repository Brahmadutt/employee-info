package com.wow.employee.info.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    @JsonProperty("house")
    private String house;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zipcode")
    private String zipcode;

    @JsonProperty("country")
    private String country;

}
