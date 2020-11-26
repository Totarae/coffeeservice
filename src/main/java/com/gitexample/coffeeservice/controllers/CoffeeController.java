package com.gitexample.coffeeservice.controllers;

import com.gitexample.coffeeservice.models.Coffee;
import com.gitexample.coffeeservice.services.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CoffeeController {

    @Autowired
    private final CoffeeService reps;

    public CoffeeController(CoffeeService rcpS) {
        this.reps = rcpS;
    }

    @PostMapping(value="/coffee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Coffee create(@Valid @RequestBody Coffee coffee, Errors errors) throws ValidationException {

        if (errors.hasErrors()) {
            // Extract ConstraintViolation list from body errors
            List<ConstraintViolation<?>> violationsList = new ArrayList<>();
            for (ObjectError e : errors.getAllErrors()) {
                violationsList.add(e.unwrap(ConstraintViolation.class));
            }

            String exceptionMessage = "";

            // Construct a helpful message for each input violation
            for (ConstraintViolation<?> violation : violationsList) {
                exceptionMessage += violation.getMessage() + System.lineSeparator();
            }
            throw new ValidationException(exceptionMessage);
        }

        return reps.save(coffee);
    }

    @ResponseBody
    @GetMapping(value="/coffeeList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Coffee> get(Model model) {
        return reps.findAll();
    }

    @GetMapping("/coffeeList/pretty")
    public String Pretty(Model model) {
        model.addAttribute("coffee", reps.findAll());
        return "coffee/list";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String, Object> ValidationException(ValidationException ex){
        Map<String, Object> map = new HashMap<>();
        map.put("error", ex.getMessage());
        map.put("status", HttpStatus.BAD_REQUEST.value());
        return map;
    }
}
