package com.ocrooms.safetynet.mapper;

import com.ocrooms.safetynet.dto.PersonDto;
import com.ocrooms.safetynet.dto.PersonListDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonListDtoMapper {
    public PersonListDto map(List<PersonDto> personDtos, List<Integer> agesList) {

        long countPersonsUnder18 = agesList.stream()
                .filter(age -> age < 18)
                .count();

        long countPersons18AndAbove = agesList.stream()
                .filter(age -> age >= 18)
                .count();

        return new PersonListDto(
                personDtos,
                countPersonsUnder18,
                countPersons18AndAbove
        );
    }
}