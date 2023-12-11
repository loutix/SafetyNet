package com.ocrooms.safetynet.mapper;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject {
    List<Person> persons;
    List<Firestation> firestations;
    List<Medicalrecords> medicalrecords;
}
