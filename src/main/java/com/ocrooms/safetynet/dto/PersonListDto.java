package com.ocrooms.safetynet.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class PersonListDto {

    long nbrMajor;
    long nbrMinor;

    List<PersonDto> personDtoList;

    public PersonListDto(List<PersonDto> personDtoList, long under18, long above18) {
        this.personDtoList = personDtoList;
    }
}
