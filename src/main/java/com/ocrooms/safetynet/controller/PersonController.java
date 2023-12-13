package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.PersonService;
import jakarta.validation.Valid;
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
    public @ResponseBody List<Person> index() {
        logger.info("GET /person");
        return personService.index();
    }

    @GetMapping("/show")
    public @ResponseBody Person show(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("GET person/show?firstName=" + firstName +"&lastname="+lastName);
        return personService.show(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Person create(@Valid  @RequestBody Person person) {
        logger.info("POST person: " + person);
        return personService.create(person);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam String firstName, @RequestParam String lastName, @Valid @RequestBody Person person) {
        logger.info("PUT person?firstName=" + firstName +"&firstName="+lastName + "(@RequestBody: " + person);
        this.personService.update(firstName, lastName, person);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("DELETE person?firstName=" + firstName +"&firstName="+lastName);
        this.personService.delete(firstName, lastName);
    }

}
