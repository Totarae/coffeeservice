package com.gitexample.coffeeservice.services;

import com.gitexample.coffeeservice.models.Coffee;
import com.gitexample.coffeeservice.repos.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoffeeServiceImpl  implements CoffeeService {
    private final CoffeeRepository coffeerepo;

    public CoffeeServiceImpl(CoffeeRepository coffeerepo) {
        this.coffeerepo = coffeerepo;
    }

    @Override
    @Transactional
    public Coffee save(Coffee coffee) {
        Optional<Coffee> recipeOptional = coffeerepo.findById(coffee.getId().toString());
        Coffee temp = coffeerepo.save(coffee);
        return temp;
    }

    @Override
    public List<Coffee> findAll() {
        List<Coffee> temp = coffeerepo.findAll();
        return temp;
    }

    @Override
    public Coffee findById(Long aLong) {
        return coffeerepo.findAllById(aLong);
    }
}
