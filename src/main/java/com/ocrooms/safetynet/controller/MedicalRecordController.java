package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "medicalRecord")
public class MedicalRecordController {

    private final static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<Medicalrecords> index() {
        logger.info("GET /medicalRecord");
        return this.medicalRecordService.index();
    }

    @GetMapping("/show")
    public Medicalrecords show(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("GET /medicalRecord?firstName=" + firstName + "&lastName=" + lastName);
        return this.medicalRecordService.show(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Medicalrecords create(@Valid @RequestBody Medicalrecords medicalrecords) {
        logger.info("POST /medicalRecord: " + "  @RequestBody: " + medicalrecords);
        return this.medicalRecordService.create(medicalrecords);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam String firstName, @RequestParam String lastName, @Valid @RequestBody Medicalrecords medicalrecords) {
        logger.info("PUT medicalRecord?firstName=" + firstName + "&lastName=" + lastName + "@RequestBody: " + medicalrecords);
        this.medicalRecordService.update(firstName, lastName, medicalrecords);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("DELETE medicalRecord?firstName=" + firstName + "&lastName=" + lastName);
        this.medicalRecordService.delete(firstName, lastName);
    }

}
