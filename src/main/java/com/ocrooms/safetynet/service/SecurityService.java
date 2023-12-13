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
    private final JsonService jsonService;


    public SecurityService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    /**
     * Return a persons list served by the station number
     *
     * @param station
     * @return
     */
    public PersonListDto searchFirestation(Integer station) {
        if (station == null) {
            throw new RuntimeException("Bad request : station can not be null");
        }

        //Set<String> stationsList = jsonService.getData().getFirestations().stream()
        Set<String> stationsList = jsonService.getData().getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(station))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        //List<PersonDto> personDto = jsonService.getData().getPersons().stream()
        List<PersonDto> personDto = jsonService.getData().getPersons().stream()
                .filter(person -> stationsList.contains(person.getAddress()))
                .flatMap(person ->
                        jsonService.getData().getMedicalrecords().stream()
                                .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                                .map(medicalrecords -> new PersonDto(person))
                )
                .toList();

        long nbrMajor = personDto.stream()
                .flatMap(person ->
                        jsonService.getData().getMedicalrecords().stream()
                                .filter(mr -> mr.getFirstName().equals(person.getFirstName()) && mr.getLastName().equals(person.getLastName()))
                                .filter(mr -> mr.isMajor())
                ).count();

        return new PersonListDto(personDto, nbrMajor);
    }

    /**
     * Return a children list living at this address
     *
     * @param address
     * @return
     */
    public List<ChildDto> searchChildAlert(String address) {
        if (address == null) {
            throw new RuntimeException("Bad request : address can not be null");
        }
        return jsonService.getData().getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .flatMap(person ->
                        jsonService.getData().getMedicalrecords().stream()
                                .filter(mr ->
                                        mr.getFirstName().equals(person.getFirstName()) &&
                                                mr.getLastName().equals(person.getLastName()) &&
                                                mr.isMinor())
                                .map(mr -> new ChildDto(
                                        mr,
                                        jsonService.getData().getPersons().stream()
                                                .filter(per -> per.getLastName().equals(mr.getLastName())).toList()))
                )
                .toList();
    }

    /**
     * Return a list of phone numbers served by the firestation number.
     *
     * @param firestationNumber
     * @return
     */
    public List<String> searchPhoneAlert(Integer firestationNumber) {
        if (firestationNumber == null) {
            throw new RuntimeException("Bad request : firestationNumber can not be null");
        }

        return jsonService.getData().getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(firestationNumber))
                .flatMap(firestation -> jsonService.getData().getPersons().stream()
                        .filter(person -> person.getAddress().equals(firestation.getAddress()))
                        .map(person -> person.getPhone())
                ).toList();
    }


    /**
     * Return a list of persons living at this address
     *
     * @param address
     * @return
     */
    public List<PersonAddressStationDto> searchFire(String address) {
        if (address == null) {
            throw new RuntimeException("Bad request : address can not be null");
        }

        return jsonService.getData().getPersons().stream()
                .filter(person -> person.getAddress().equals(address))

                .flatMap(person -> jsonService.getData().getFirestations().stream()
                        .filter(firestation -> firestation.getAddress().equals(person.getAddress()))
                        .flatMap(firestation -> jsonService.getData().getMedicalrecords().stream()
                                .filter(medicalrecords -> medicalrecords.getFirstName().equals(person.getFirstName()) &&
                                        medicalrecords.getLastName().equals(person.getLastName()))
                                .map(medicalrecords -> new PersonAddressStationDto(person, firestation, medicalrecords))
                        )

                ).toList();
    }

    /**
     * Return  a list persons served by the firestation List number.
     *
     * @param stations
     * @return
     */
    public List<FloodDto> searchFlood(List<Integer> stations) {

        List<String> adressesFirestations =
                jsonService.getData().getFirestations().stream()
                        .filter(firestation -> stations.contains(firestation.getStation()))
                        .map(firestation -> firestation.getAddress()).toList();


        List<FloodDto> FloodDtoList = new ArrayList<>();

        for (String adresse : adressesFirestations) {

            List<FloodPersonDto> floodPersonDtoList = jsonService.getData().getPersons().stream()
                    .filter(person -> person.getAddress().equals(adresse))
                    .flatMap(person -> jsonService.getData().getMedicalrecords().stream()
                            .filter(medicalrecords -> medicalrecords.getFirstName().equals(person.getFirstName()) && medicalrecords.getLastName().equals(person.getLastName()))
                            .map(medicalrecords -> new FloodPersonDto(person, medicalrecords))
                    ).toList();


            FloodDtoList.add(new FloodDto(adresse, floodPersonDtoList));

        }
        return FloodDtoList;
    }


    /**
     * Return persons info from the same family
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public List<PersonInfoDto> searchPersonInfo(String firstName, String lastName) {

        return jsonService.getData().getPersons().stream().filter(person -> person.getLastName().equals(lastName))
                .flatMap(person -> jsonService.getData().getMedicalrecords().stream()
                        .filter(medicalrecords -> medicalrecords.getFirstName().equals(person.getFirstName()) && medicalrecords.getLastName().equals(lastName))
                        .map(medicalrecords -> new PersonInfoDto(person, medicalrecords))
                ).toList();

    }


    /**
     * Return all emails of the city
     *
     * @param city
     * @return
     */
    public List<String> searchEmail(String city) {
        if (city == null) {
            throw new RuntimeException("Bad request : city can not be null");
        }
        return jsonService.getData().getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .map(person -> person.getEmail()).toList();
    }

}
