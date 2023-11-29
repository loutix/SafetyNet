package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Medicalrecords;
import com.ocrooms.safetynet.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "medicalRecord")
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<Medicalrecords> index() {
        return this.medicalRecordService.index();
    }

    @GetMapping(path = "{firstName}/{lastName}")
    public Medicalrecords show(@PathVariable String firstName, @PathVariable String lastName) {
        return this.medicalRecordService.show(firstName, lastName);
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody Medicalrecords medicalrecords) {
        this.medicalRecordService.create(medicalrecords);
    }


    @PutMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Medicalrecords medicalrecords) {
        this.medicalRecordService.update(firstName, lastName, medicalrecords);
    }

    @DeleteMapping(path = "{firstName}/{lastName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String firstName, @PathVariable String lastName) {
        this.medicalRecordService.delete(firstName, lastName);
    }

}
