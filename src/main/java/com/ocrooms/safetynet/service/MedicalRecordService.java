package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import com.ocrooms.safetynet.service.exceptions.ItemAlreadyExists;
import com.ocrooms.safetynet.service.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private final JsonService jsonService;
    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);


    public MedicalRecordService(JsonService jsonService) {
        this.jsonService = jsonService;

    }

    public List<Medicalrecords> index() {
        return this.jsonService.getData().getMedicalrecords();
    }


    public Medicalrecords show(String firstName, String lastName) {
        return jsonService.getData().getMedicalrecords().stream()
                .filter(medicalrecords -> medicalrecords.getFirstName().equalsIgnoreCase(firstName.trim()) && medicalrecords.getLastName().equalsIgnoreCase(lastName.trim()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("The medical record with the name: " + firstName + " " + lastName + " is not found"));
    }

    public Medicalrecords create(Medicalrecords medicalrecords) {

        medicalrecords.trimProperties();

        if (jsonService.getData().getMedicalrecords().stream().anyMatch(mr -> mr.getFirstName().equalsIgnoreCase(medicalrecords.getFirstName()) && mr.getLastName().equalsIgnoreCase(medicalrecords.getLastName()))) {
            throw new ItemAlreadyExists("Bad request:" + medicalrecords + " already exist");
        }
        Person personExist = jsonService.getData().getPersons().stream()
                .filter(person -> person.getFirstName().equals(medicalrecords.getFirstName()) && person.getLastName().equals(medicalrecords.getLastName()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Bad request: create person before his medicalrecords"));

        this.jsonService.getData().getMedicalrecords().add(medicalrecords);

        return medicalrecords;
    }


    public void update(String firstName, String lastName, Medicalrecords medicalrecords) {

        Medicalrecords medicalrecordToUpdate = this.show(firstName.trim(), lastName.trim());

        medicalrecords.trimProperties();

        if (medicalrecordToUpdate.getFirstName().equals(medicalrecords.getFirstName()) && medicalrecordToUpdate.getLastName().equals(medicalrecords.getLastName())) {
            medicalrecordToUpdate.setBirthdate(medicalrecords.getBirthdate());
            medicalrecordToUpdate.setMedications(medicalrecords.getMedications());
            medicalrecordToUpdate.setAllergies(medicalrecords.getAllergies());
        } else {
            throw new ItemNotFoundException("@RequestParam : " + firstName + " " + lastName + " is different to @RequestBody " + medicalrecordToUpdate.getFirstName() + " " + medicalrecordToUpdate.getLastName());
        }
    }

    public void delete(String firstName, String lastName) {
        jsonService.getData().getMedicalrecords().removeIf(medicalrecords -> medicalrecords.getFirstName().equalsIgnoreCase(firstName.trim()) && medicalrecords.getLastName().equalsIgnoreCase(lastName.trim()));
    }


}
