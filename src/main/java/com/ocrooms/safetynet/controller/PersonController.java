package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "person")
public class PersonController {
    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public @ResponseBody List<Person> allPersons() {
        logger.info("GET /person");
        return personService.allPersons();
    }

    @GetMapping(path = "{firstName}/{lastName}")
    public @ResponseBody Person show(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("GET /person/{id}/{id_2}: " + firstName +"/"+lastName);
        return personService.show(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Person create(@RequestBody Person person) {
        logger.info("POST /person: " + person);
        return personService.create(person);
    }

    @PutMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Person person) {
        logger.info("PUT /person/{id}/{id_2}: " + firstName +"/"+lastName + "@RequestBody: " + person);
        this.personService.update(firstName, lastName, person);
    }

    @DeleteMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("DELETE /person/{id}/{id_2}: " + firstName +"/"+lastName);
        this.personService.delete(firstName, lastName);
    }

}
