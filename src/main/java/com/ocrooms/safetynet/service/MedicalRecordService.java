package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final List<Medicalrecords> medicalrecordsList;
    private final List<Person> personsList;

    public MedicalRecordService(JsonService jsonService, PersonService personService) {
        this.medicalrecordsList = jsonService.readJsonFileMedicalrecords();
        this.personsList = personService.allPersons();
    }

    public List<Medicalrecords> index() {
        return this.medicalrecordsList;
    }


    public Medicalrecords show(String firstName, String lastName) {
        Optional<Medicalrecords> optionalMedicalrecords =
                this.medicalrecordsList.stream()
                        .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                        .findFirst();

        if (optionalMedicalrecords.isPresent()) {
            return optionalMedicalrecords.get();
        } else {
            return null;
        }
    }

    public void create(Medicalrecords medicalrecords) {
        // control person exist before created his medical record
        Optional<Person> personExist = this.personsList.stream()
                .filter(person -> person.getFirstName().equals(medicalrecords.getFirstName()) && person.getLastName().equals(medicalrecords.getLastName()))
                .findFirst();

        if (personExist.isPresent()) {
            //control person has not a medical record
            Medicalrecords medicalrecordsToCreate = this.show(medicalrecords.getFirstName(), medicalrecords.getLastName());
            if (Objects.isNull(medicalrecordsToCreate)) {
                this.medicalrecordsList.add(medicalrecords);
            }
        }
    }

    public void update(String firstName, String lastName, Medicalrecords medicalrecords) {
        Medicalrecords medicalrecordsToUpdate = this.show(firstName, lastName);

        if (!Objects.isNull(medicalrecordsToUpdate)) {
            medicalrecordsToUpdate.setBirthdate(medicalrecords.getBirthdate());
            medicalrecordsToUpdate.setMedications(medicalrecords.getMedications());
            medicalrecordsToUpdate.setAllergies(medicalrecords.getAllergies());
        }
    }

    public void delete(String firstName, String lastName) {
        Medicalrecords medicalrecords = this.show(firstName, lastName);

        if (!Objects.isNull(medicalrecords)) {
            this.medicalrecordsList.remove(medicalrecords);
        }
    }


}
