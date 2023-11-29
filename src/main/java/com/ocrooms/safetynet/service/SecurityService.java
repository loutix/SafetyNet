package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.dto.ChildDto;
import com.ocrooms.safetynet.dto.PersonDto;
import com.ocrooms.safetynet.dto.PersonListDto;
import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.mapper.ChildDtoMapper;
import com.ocrooms.safetynet.mapper.PersonDtoMapper;
import com.ocrooms.safetynet.mapper.PersonListDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SecurityService {

    private final List<Firestation> firestationsList;
    private final List<Person> personsList;
    private final List<Medicalrecords> medicalrecordsList;

    @Autowired
    private PersonDtoMapper personDtoMapper;

    @Autowired
    private PersonListDtoMapper personListDtoMapper;

    @Autowired
    private ChildDtoMapper childDtoMapper;


    public SecurityService(JsonService jsonService) {
        this.firestationsList = jsonService.readJsonFileFirestations();
        this.personsList = jsonService.readJsonFilePersons();
        this.medicalrecordsList = jsonService.readJsonFileMedicalrecords();
    }


    /**
     * Return a list of person covered by the corresponding fire station.
     * @RequestParam(required = true) Integer station
     * @return PersonListDto
     */
    public PersonListDto searchFirestation(Integer station) {
        if (station != null) {
            Set<String> stationsList = firestationsList.stream()
                    .filter(firestation -> firestation.getStation() == (station))
                    .map(Firestation::getAddress)
                    .collect(Collectors.toSet());

            List<PersonDto> personDto = personsList.stream()
                    .filter(person -> stationsList.contains(person.getAddress()))
                    .flatMap(person ->
                            medicalrecordsList.stream()
                                    .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                                    .map(medicalrecords -> personDtoMapper.map(person))
                    )
                    .toList();

            List<Integer> agesList = personDto.stream()
                    .flatMap(person ->
                            medicalrecordsList.stream()
                                    .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                                    .map(mr -> mr.calculateAge())
                    ).toList();

            return personListDtoMapper.map(personDto, agesList);

        } else {
            return null;
        }
    }

    /**
     * Return a list of children living at this address.
     * @RequestParam(required = true) String address
     * @return List<ChildDto>
     */
    public List<ChildDto> searchChildAlert(String address) {
        if (address != null) {
            List<ChildDto> childToDto = personsList.stream()
                    .filter(person -> person.getAddress().equals(address))
                    .flatMap(person ->
                            medicalrecordsList.stream()
                                    .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                                    .map(mr -> childDtoMapper.map(mr, personsList))
                    )
                    .toList();

            return childToDto.stream().filter(childDto -> childDto.getAge() < 18).toList();

        }
        return null;
    }
}
