package com.gitexample.coffeeservice.services;

import com.gitexample.coffeeservice.models.Coffee;

import java.util.List;

public interface CoffeeService  {
    Coffee save(Coffee coffee);
    List<Coffee> findAll();
    Coffee findById(Long aLong);
}
