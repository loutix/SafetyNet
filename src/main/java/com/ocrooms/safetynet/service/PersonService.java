package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PersonService {

    private final List<Person> personsList;

    public PersonService(JsonService jsonService) {
        this.personsList = jsonService.readJsonFilePersons();
    }

    public List<Person> allPersons() {
        return personsList;
    }

    public Person show(String firstName, String lastName) {
        Optional<Person> optionalPerson =
                personsList.stream()
                        .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                        .findFirst();

        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        } else {
            return null;
        }
    }

    public void create(Person person) {
        personsList.add(person);
    }

    public void update(String firstName, String lastName, Person person) {
        Person personToUpdate = show(firstName, lastName);
        if (!Objects.isNull(person)) {
            personToUpdate.setAddress(person.getAddress());
            personToUpdate.setCity(person.getCity());
            personToUpdate.setZip(person.getZip());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setEmail(person.getEmail());
        }
    }

    public void delete(String firstName, String lastName) {
        Person person = show(firstName, lastName);
        personsList.remove(person);
    }
}
