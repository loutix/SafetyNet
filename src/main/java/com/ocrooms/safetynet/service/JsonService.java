package com.ocrooms.safetynet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.entities.Person;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JsonService {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public JsonService(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public Set<Firestation> readJsonFileFirestations() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data.json");
            Map<String, Set<Firestation>> dataMap = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, Set<Firestation>>>() {
            });




            return dataMap.get("firestations");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> readJsonFilePersons() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data.json");
            Map<String, List<Person>> dataMap = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, List<Person>>>() {
            });

            return dataMap.get("persons");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Medicalrecords> readJsonFileMedicalrecords() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data.json");
            Map<String, List<Medicalrecords>> dataMap = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, List<Medicalrecords>>>() {
            });

            return dataMap.get("medicalrecords");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}