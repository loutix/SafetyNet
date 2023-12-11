package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FireStationService {

    private final static Logger logger = LoggerFactory.getLogger(FireStationService.class);
    private final Set<Firestation> firestationList;

    public FireStationService(JsonService jsonService) {
        this.firestationList = jsonService.readJsonFileFirestations();
    }

    public Set<Firestation> index() {
        return this.firestationList;
    }

    public List<Firestation> show(String address) {
        return firestationList.stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .toList();
    }

    public Firestation create(Firestation firestation) {

        if (((firestation.getAddress() == null) || (firestation.getStation() == null)) || (firestation.getAddress().isEmpty() || firestation.getStation() <= 0)) {
            logger.error("Missing or null params firestation :");
            throw new RuntimeException("Bad request: params cannot be null or empty");
        }

        if (firestationList.contains(firestation)) {
            logger.error("Bad request: the firestation already exist");
            throw new RuntimeException("Bad request: the firestation already exist");
        } else {
            firestationList.add(firestation);
            return firestation;
        }

    }


    public void update(String address, Firestation.FirestationUpdateRequest firestationUpdateRequest) {

        Firestation firestationToUpdate = show(address).stream()
                .filter(firestation -> firestation.getStation().equals(firestationUpdateRequest.getOldStation()))
                .findFirst()
                .orElse(null);

        if (firestationToUpdate == null) {
            logger.error("Bad request: the firestation to update does not exist\"");
            throw new RuntimeException("Bad request: the firestation to update does not exist");
        }

        if (firestationUpdateRequest.getNewStation() != null && firestationUpdateRequest.getNewStation() >= 1) {
            firestationToUpdate.setStation(firestationUpdateRequest.getNewStation());
        }

    }


    public void delete(String address) {
        if (address == null || address.isEmpty()) {
            logger.error("Bad request: params cannot be null or empty");
            throw new RuntimeException("Bad request: params cannot be null or empty");
        }
        firestationList.removeIf(firestation -> firestation.getAddress().equals(address));
    }


}
