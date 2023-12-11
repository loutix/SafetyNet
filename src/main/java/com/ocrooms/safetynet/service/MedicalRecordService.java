package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
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
        return medicalrecordsList.stream()
                .filter(medicalrecords -> medicalrecords.getFirstName().equals(firstName) && medicalrecords.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public Medicalrecords create(Medicalrecords medicalrecords) {
        // control medicalrecords does not exist
        if (this.show(medicalrecords.getFirstName(), medicalrecords.getLastName()) != null) {
            logger.error("Bad request: the medicalrecords already exist");
            throw new RuntimeException("Bad request: the medicalrecords already exist");
        }
        // control person exist before created his medical record
        Person personExist = personsList.stream()
                .filter(person -> person.getFirstName().equals(medicalrecords.getFirstName()) && person.getLastName().equals(medicalrecords.getLastName()))
                .findFirst()
                .orElse(null);

        if (personExist == null) {
            logger.error("Bad request: create person before his medicalrecords");
            throw new RuntimeException("Bad request: create person before his medicalrecords");
        } else {
            this.medicalrecordsList.add(medicalrecords);
            return medicalrecords;
        }
    }


    public void update(String firstName, String lastName, Medicalrecords medicalrecords) {

        if (!firstName.equals(medicalrecords.getFirstName()) || !lastName.equals(medicalrecords.getLastName())) {
            logger.error("Bad request: route id is different to person id");
            throw new RuntimeException("Bad request: route id is different to person id");
        }

        Medicalrecords medicalrecordsToUpdate = this.show(firstName, lastName);

        if (medicalrecordsToUpdate == null) {
            logger.error("Bad request: the medicalrecords does not exist");
            throw new RuntimeException("Bad request: the medicalrecords does not exist");
        }

        medicalrecordsToUpdate.setBirthdate(medicalrecords.getBirthdate());
        medicalrecordsToUpdate.setMedications(medicalrecords.getMedications());
        medicalrecordsToUpdate.setAllergies(medicalrecords.getAllergies());

    }

    public void delete(String firstName, String lastName) {

        if ((firstName == null || lastName == null) || (firstName.isEmpty() || lastName.isEmpty())) {
            logger.error("Bad request: params cannot be null or empty");
            throw new RuntimeException("Bad request: params cannot be null or empty");
        }
        medicalrecordsList.removeIf(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName));
    }


}
