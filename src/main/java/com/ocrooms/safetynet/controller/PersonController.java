package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "person")
public class PersonController {
    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public @ResponseBody List<Person> allPersons() {
        return personService.allPersons();
    }

    @GetMapping(path = "{firstName}/{lastName}")
    public @ResponseBody Person show(@PathVariable String firstName, @PathVariable String lastName) {
        return personService.show(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody Person person) {
        this.personService.create(person);
    }

    @PutMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Person person) {
        this.personService.update(firstName, lastName, person);
    }

    @DeleteMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String firstName, @PathVariable String lastName) {
        this.personService.delete(firstName, lastName);
    }

}
