package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {
    private final static Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final List<Person> personsList;

    public PersonService(JsonService jsonService) {
        this.personsList = jsonService.readJsonFilePersons();
    }

    public List<Person> allPersons() {
        return personsList;
    }


    public Person show(String firstName, String lastName) {
        return personsList.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public Person create(Person person) {
        if ((person.getFirstName() != null && person.getLastName() != null) && (!person.getFirstName().isEmpty() && !person.getLastName().isEmpty())) {
            if (personsList.contains(person)) {
                logger.error("Bad request: the person already exist");
                throw new RuntimeException("Bad request: the person already exist");
            } else {
                personsList.add(person);
                return person;
            }
        }
        return null;
    }

    public void update(String firstName, String lastName, Person person) {

        if (!firstName.equals(person.getFirstName()) || !lastName.equals(person.getLastName())) {
            logger.error("Bad request: route id is different to person id");
            throw new RuntimeException("Bad request: route id is different to person id");
        }

        if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
            logger.error("Bad request: params cannot be empty");
            throw new RuntimeException("Bad request: params cannot be empty");
        }

        Person personToUpdate = this.show(person.getFirstName(), person.getLastName());
        if (personToUpdate == null) {
            logger.error("Bad request: the person does not exist");
            throw new RuntimeException("Bad request: the person does not exist");
        } else {
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());

        }
    }

    public void delete(String firstName, String lastName) {
        if ((firstName == null || lastName == null) || (firstName.isEmpty() || lastName.isEmpty())) {
            logger.error("Bad request: params cannot be null or empty");
            throw new RuntimeException("Bad request: params cannot be null or empty");
        }
        personsList.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }
}

