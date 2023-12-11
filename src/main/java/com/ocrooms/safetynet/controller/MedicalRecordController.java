package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.service.MedicalRecordService;
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

    @GetMapping(path = "{firstName}/{lastName}")
    public Medicalrecords show(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("GET /medicalRecord/{id}/{id_2}: " + firstName +" / "+ lastName);
        return this.medicalRecordService.show(firstName, lastName);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Medicalrecords create(@RequestBody Medicalrecords medicalrecords) {
        logger.info("POST /medicalRecord: " + "  @RequestBody: " + medicalrecords);
        return this.medicalRecordService.create(medicalrecords);
    }


    @PutMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Medicalrecords medicalrecords) {
        logger.info("PUT /medicalRecord/{id}/{id_2}: " + firstName +" / "+ lastName + " + @RequestBody: " + medicalrecords);
        this.medicalRecordService.update(firstName, lastName, medicalrecords);
    }

    @DeleteMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("DELETE /medicalRecord/{id}/{id_2}: " + firstName +" / "+ lastName);
        this.medicalRecordService.delete(firstName, lastName);
    }

}
