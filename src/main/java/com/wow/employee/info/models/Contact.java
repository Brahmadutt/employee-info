package com.wow.employee.info.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;

}
