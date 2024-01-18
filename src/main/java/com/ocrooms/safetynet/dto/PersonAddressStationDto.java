package com.ocrooms.safetynet.dto;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.entities.MedicalRecord;
import com.ocrooms.safetynet.entities.Person;
import lombok.Data;

import java.util.List;

@Data
public class PersonAddressStationDto {

    String lastName;
    String phone;
    int age;
    int stationNumber;
    Firestation firestation;
    List<String> medications;
    List<String> allergies;

    public PersonAddressStationDto(Person person, Firestation firestation, MedicalRecord medicalrecords) {
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = medicalrecords.calculateAge();
        this.stationNumber = firestation.getStation();
        this.firestation = firestation;
        this.medications = medicalrecords.getMedications();
        this.allergies = medicalrecords.getAllergies();

    }
}
