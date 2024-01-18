package com.ocrooms.safetynet.dto;

import com.ocrooms.safetynet.entities.MedicalRecord;
import com.ocrooms.safetynet.entities.Person;
import lombok.Data;

import java.util.List;

@Data
public class FloodPersonDto {
    String firstName;
    String lastName;
    String phone;
    Integer age;
    List<String> medications;
    List<String> allergies;

    public FloodPersonDto(Person person, MedicalRecord medicalrecords) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = medicalrecords.calculateAge();
        this.medications = medicalrecords.getMedications();
        this.allergies = medicalrecords.getAllergies();
    }
}
