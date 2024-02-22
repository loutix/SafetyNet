package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.MedicalRecord;
import com.ocrooms.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "medical-record")
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody MedicalRecord create(@Valid @RequestBody MedicalRecord medicalrecords) {
        log.info("POST/medical-record: " + "  @RequestBody: " + medicalrecords);
        return this.medicalRecordService.create(medicalrecords);
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @Valid @RequestBody MedicalRecord medicalrecords) {
        log.info("PUT/medical-record/{id}=" + id + "(@RequestBody: " + medicalrecords);

        this.medicalRecordService.update(id, medicalrecords);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        log.info("DELETE/medical-record/{id}=" + id);
        this.medicalRecordService.delete(id);
    }

}
