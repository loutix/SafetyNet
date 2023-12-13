package com.ocrooms.safetynet.service;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.exceptions.ItemAlreadyExists;
import com.ocrooms.safetynet.service.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FireStationService {

    private final static Logger logger = LoggerFactory.getLogger(FireStationService.class);

    private final JsonService jsonService;

    public FireStationService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public Set<Firestation> index() {
        return jsonService.getData().getFirestations();
    }

    public List<Firestation> show(String address) {

        List<Firestation> firestationsList = jsonService.getData().getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equalsIgnoreCase(address.trim()))
                .toList();

        if (firestationsList.isEmpty()) {
            throw new ItemNotFoundException("There is no fire station at this address: " + address);
        }

        return firestationsList;
    }

    public Firestation create(Firestation firestation) {
        firestation.trimProperties();
        if (jsonService.getData().getFirestations().contains(firestation)) {
            throw new ItemAlreadyExists("Bad request:"+ firestation + " already exist");
        } else {
            jsonService.getData().getFirestations().add(firestation);
            return firestation;
        }
    }

    public void update(String address, Firestation.FirestationUpdateRequest firestationUpdateRequest) {

        Firestation firestationToUpdate = show(address.trim()).stream()
                .filter(firestation -> firestation.getStation().equals(firestationUpdateRequest.getOldStation()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("There is no fire station with number " + firestationUpdateRequest.getOldStation() + " at the address: " + address));

        firestationToUpdate.setStation(firestationUpdateRequest.getNewStation());
    }

    public void delete(String address, Integer station) {

        if(address!= null && station != null){
            jsonService.getData().getFirestations().removeIf(firestation -> firestation.getAddress().equalsIgnoreCase(address.trim()) && firestation.getStation().equals(station));
        } else if (address != null) {
            jsonService.getData().getFirestations().removeIf(firestation -> firestation.getAddress().equalsIgnoreCase(address.trim()));
        } else if (station != null) {
            jsonService.getData().getFirestations().removeIf(firestation -> firestation.getStation().equals(station));
        }
    }


}
