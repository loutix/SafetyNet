package com.ocrooms.safetynet.mapper;

import com.ocrooms.safetynet.dto.ChildDto;
import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChildDtoMapper {

    public ChildDto map(Medicalrecords medicalrecords, List<Person> personsList) {

        List<Person> family = personsList.stream()
                .filter(person -> person.getLastName().contains(medicalrecords.getLastName()))
                .toList();


        return new ChildDto(
                medicalrecords.getFirstName(),
                medicalrecords.getLastName(),
                medicalrecords.calculateAge(),
                family
        );
    }
}
