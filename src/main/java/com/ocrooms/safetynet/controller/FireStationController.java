package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.FireStationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "firestations")
public class FireStationController {
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Firestation create(@Valid @RequestBody Firestation firestation) {
        log.info("POST /firestations: " + firestation);
        return this.fireStationService.create(firestation);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam String address, @Valid @RequestBody Firestation.FirestationUpdateRequest firestationUpdateRequest) {
        log.info("PUT /firestations?address=" + address + "@RequestBody: " + firestationUpdateRequest);
        this.fireStationService.update(address, firestationUpdateRequest);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String address, @RequestParam Integer station) {
        log.info("DELETE /firestations?address=" + address + "&station=" + station);
        this.fireStationService.delete(address, station);
    }
}








