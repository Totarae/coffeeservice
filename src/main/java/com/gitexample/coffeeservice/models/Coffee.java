package com.gitexample.coffeeservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Document(collection = "coffee")
public class Coffee {
    @Id
    @Field("_id")
    private Long id;

    @NotNull(message = "Name must not be null")
    private String name;
    private String sort;

    @NotNull(message = "Cost must not be null")
    @Min(value = 1, message = "Cost should not be less than 1")
    private Double cost;

    private String country;
}
