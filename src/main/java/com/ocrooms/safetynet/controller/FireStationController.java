package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "firestations")
public class FireStationController {

    private final static Logger logger = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    public Set<Firestation> index() {
        logger.info("GET /firestations");
        return this.fireStationService.index();
    }

    @GetMapping(path = "{address}")
    public @ResponseBody List<Firestation> show(@PathVariable String address) {
        logger.info("GET /firestations/{id}: " + address);
        return fireStationService.show(address);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Firestation create(@RequestBody Firestation firestation) {
        logger.info("POST /firestations: " + firestation);
        return this.fireStationService.create(firestation);
    }

    @PutMapping(path = "{address}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String address, @RequestBody Firestation.FirestationUpdateRequest firestationUpdateRequest) {
        logger.info("PUT /firestations/{id}: " + address + " + @RequestBody: " + firestationUpdateRequest);
        this.fireStationService.update(address, firestationUpdateRequest);
    }

    @DeleteMapping(path = "{address}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String address) {
        logger.info("DELETE /firestations/{id}: " + address);
        this.fireStationService.delete(address);
    }
}
