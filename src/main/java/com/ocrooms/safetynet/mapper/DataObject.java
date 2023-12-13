package com.ocrooms.safetynet.mapper;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject {
    List<Person> persons;
    Set<Firestation> firestations;
    List<Medicalrecords> medicalrecords;
}
