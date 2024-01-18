package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.dto.*;
import com.ocrooms.safetynet.entities.Firestation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SecurityService {

    private final FireStationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public SecurityService(FireStationRepository firestationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    /**
     * Return a list of persons who are served by the station number
     *
     * @param station station
     * @return return PersonListDto
     */
    public PersonListDto searchFirestation(Integer station) {

        Set<String> stationsList = firestationRepository.findAllByStation(station)
                .map(Firestation::getAddress).collect(Collectors.toSet());

        List<PersonDto> personDto =
                personRepository.findAll()
                        .filter(person -> stationsList.contains(person.getAddress()))
                        .flatMap(person -> medicalRecordsRepository.findAllByPerson(person)
                                .map(medicalrecords -> new PersonDto(person))
                        )
                        .toList();

        long nbrMajor = personDto.stream()
                .flatMap(dto -> medicalRecordsRepository.findAllByPersonDto(dto)
                        .filter(MedicalRecord::isMajor)
                ).count();

        return new PersonListDto(personDto, nbrMajor);
    }

    /**
     * Return a children list living at this address
     *
     * @param address address
     * @return List<ChildDto>
     */
    public List<ChildDto> searchChildAlert(String address) {

        return personRepository.findAllByAddress(address)
                .flatMap(person ->
                        medicalRecordsRepository.findAllByPerson(person)
                                .filter(MedicalRecord::isMinor)
                                .map(medicalrecords -> new ChildDto(medicalrecords,
                                        personRepository.findAllByMedicalRecordLastName(medicalrecords).toList()))
                )
                .toList();
    }

    /**
     * Return a list of phone numbers served by the fire station number.
     *
     * @param station station
     * @return List<String>
     */
    public List<String> searchPhoneAlert(Integer station) {

        return firestationRepository.findAllByStation(station)
                .flatMap(firestation -> personRepository.findAllByFireStation(firestation)
                        .map(Person::getPhone)
                ).toList();
    }


    /**
     * Return a list of persons living at this address
     *
     * @param address address
     * @return List<PersonAddressStationDto>
     */
    public List<PersonAddressStationDto> searchFire(String address) {

        return personRepository.findAllByAddress(address)
                .flatMap(person -> firestationRepository.findAllByPerson(person)
                        .flatMap(firestation -> medicalRecordsRepository.findAllByPerson(person)
                                .map(medicalrecords -> new PersonAddressStationDto(person, firestation, medicalrecords))
                        )
                ).toList();
    }

    /**
     * Return  a list persons served by the fire station List number.
     *
     * @param stations stations
     * @return List<FloodDto>
     */
    public List<FloodDto> searchFlood(List<Integer> stations) {

        List<String> addressFirestationsList =
                firestationRepository.findAll()
                        .filter(firestation -> stations.contains(firestation.getStation()))
                        .map(Firestation::getAddress).toList();


        List<FloodDto> FloodDtoList = new ArrayList<>();

        for (String address : addressFirestationsList) {

            List<FloodPersonDto> floodPersonDtoList =
                    personRepository.findAllByAddress(address)
                            .flatMap(person -> medicalRecordsRepository.findAllByPerson(person)
                                    .map(medicalrecords -> new FloodPersonDto(person, medicalrecords))
                            ).toList();

            FloodDtoList.add(new FloodDto(address, floodPersonDtoList));

        }
        return FloodDtoList;
    }


    /**
     * Return persons info from the same family
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return List<PersonInfoDto>
     */
    public List<PersonInfoDto> searchPersonInfo(String firstName, String lastName) {

        return personRepository.findAllByLastName(lastName)
                .flatMap(person -> medicalRecordsRepository.findAllByPerson(person)
                        .map(medicalrecords -> new PersonInfoDto(person, medicalrecords))
                ).toList();
    }

    /**
     * Return all emails of the city
     *
     * @param city  city
     * @return List<String>
     */
    public List<String> searchEmail(String city) {
        return personRepository.findAllByCity(city)
                .map(Person::getEmail).toList();
    }

}
