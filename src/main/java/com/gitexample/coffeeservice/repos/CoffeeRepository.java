package com.gitexample.coffeeservice.repos;

import com.gitexample.coffeeservice.models.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CoffeeRepository extends MongoRepository<Coffee, String> {

}
