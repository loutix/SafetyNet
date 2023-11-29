package com.ocrooms.safetynet.mapper;

import com.ocrooms.safetynet.dto.PersonDto;
import com.ocrooms.safetynet.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoMapper {
    public PersonDto map(Person person) {

        return new PersonDto(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        );
    }
}


