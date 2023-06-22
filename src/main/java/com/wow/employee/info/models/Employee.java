package com.wow.employee.info.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "employee")
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
	@Id
	@JsonProperty("_id")
	@NotNull(message = "{validation.required}")
	private String id;

	@NotBlank(message = "firstName is required")
	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("dateOfBirth")
	private String dateOfBirth;

	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("position")
	private String position;

	@Min(value = 1, message = "salary is required")
	@JsonProperty("salary")
	private int salary;

	@JsonProperty("salaryType")
	private String salaryType;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("contact")
	private Contact contact;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("updatedAt")
	private String updatedAt;

}
