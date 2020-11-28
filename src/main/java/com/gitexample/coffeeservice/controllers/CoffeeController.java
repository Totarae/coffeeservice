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
import javax.validation.constraints.Min;
import java.util.*;

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
            log.error("Validation Failed!");
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
            log.error("Validation fail cause"+exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        Optional<Coffee> opt = Optional.ofNullable(reps.findById(coffee.getId()));

        opt.ifPresentOrElse(
            value -> {
                log.info("Coffee record is updated {} ", value);
                },
            () -> {
            log.info("New coffee record created");
            });

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
    @GetMapping(value="/coffee/show/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Coffee showById(@PathVariable @Min(1) Integer id){
        return reps.findById(Long.valueOf(id));
    }

    @GetMapping("/coffee/show/{id}/pretty")
    public String showByIdPretty(@PathVariable @Min(1) Integer id, Model model){
        model.addAttribute("item", reps.findById(new Long(id)));
        return "coffee/item";
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
