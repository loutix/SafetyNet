package com.ocrooms.safetynet.dto;

import java.util.List;

public class PersonListDto {

    long nbrMinor;

    long nbrMajor;
    List<PersonDto> personDtoList;

    public PersonListDto(List<PersonDto> personDtoList, long under18, long above18) {
        this.personDtoList = personDtoList;
        this.nbrMinor = under18;
        this.nbrMajor = above18;
    }

    public List<PersonDto> getPersonDtoList() {
        return personDtoList;
    }

    public void setPersonDtoList(List<PersonDto> personDtoList) {
        this.personDtoList = personDtoList;
    }

    public long getNbrMinor() {
        return nbrMinor;
    }

    public void setNbrMinor(long nbrMinor) {
        this.nbrMinor = nbrMinor;
    }

    public long getNbrMajor() {
        return nbrMajor;
    }

    public void setNbrMajor(long nbrMajor) {
        this.nbrMajor = nbrMajor;
    }
}
