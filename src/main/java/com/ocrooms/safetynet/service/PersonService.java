package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.exceptions.ItemAlreadyExists;
import com.ocrooms.safetynet.service.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {
    private final static Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final JsonService jsonService;

    public PersonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public List<Person> index() {
        return jsonService.getData().getPersons();
    }


    public Person show(String firstName, String lastName) {
        return jsonService.getData().getPersons().stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName.trim()) && person.getLastName().equalsIgnoreCase(lastName.trim()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("The person with name: " + firstName + " " + lastName + " is not found"));
    }

    public Person create(Person person) {

        person.trimProperties();

        if (jsonService.getData().getPersons().contains(person)) {
            throw new ItemAlreadyExists("Bad request:" + person + " already exist");
        } else {
            System.out.println(person.getFirstName());
            jsonService.getData().getPersons().add(person);
            return person;
        }
    }

    public void update(String firstName, String lastName, Person person) {

        Person personToUpdate = this.show(firstName.trim(), lastName.trim());

        person.trimProperties();

        if (personToUpdate.getFirstName().equals(person.getFirstName()) && personToUpdate.getLastName().equals(person.getLastName())) {
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());
        } else {
            throw new ItemNotFoundException("@RequestParam : " + firstName + " " + lastName + " is different to @RequestBody " + personToUpdate.getFirstName() + " " + personToUpdate.getLastName());
        }
    }

    public void delete(String firstName, String lastName) {
        jsonService.getData().getPersons().removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }
}

