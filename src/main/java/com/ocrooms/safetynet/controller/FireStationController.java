package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.FireStationService;
import jakarta.validation.Valid;
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

    @GetMapping(path = "/show")
    public @ResponseBody List<Firestation> show(@RequestParam String address) {
        logger.info("GET /firestations?address=" + address);
        return fireStationService.show(address);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Firestation create(@Valid @RequestBody Firestation firestation) {
        logger.info("POST /firestations: " + firestation);
        return this.fireStationService.create(firestation);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam String address, @Valid @RequestBody Firestation.FirestationUpdateRequest firestationUpdateRequest) {
        logger.info("PUT /firestations?address=" + address + " + @RequestBody: " + firestationUpdateRequest);
        this.fireStationService.update(address, firestationUpdateRequest);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(required = false) String address, @RequestParam(required = false) Integer station) {
        logger.info("DELETE /firestations?address=" + address + "&station=" + station);
        this.fireStationService.delete(address, station);
    }
}








