package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "person")
public class PersonController {
    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Person create(@Valid @RequestBody Person person) {
        log.info("POST/person @RequestBody = " + person);
        return personService.create(person);
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @Valid @RequestBody Person person) {
        log.info("PUT/person/{id}=" + id + "(@RequestBody: " + person);
        this.personService.update(id, person);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        log.info("DELETE/person/{id}=" + id);
        this.personService.deletePerson(id);
    }

}
