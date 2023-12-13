package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.dto.*;
import com.ocrooms.safetynet.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SecurityController {
    private final static Logger logger = LoggerFactory.getLogger(SecurityController.class);
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(path = "firestation")
    public @ResponseBody PersonListDto searchFirestation(@RequestParam(required = true) int station) {
        logger.info("GET /firestation?station=" + station);
        return this.securityService.searchFirestation(station);
    }

    @GetMapping(path = "childAlert")
    public @ResponseBody List<ChildDto> searchChildAlert(@RequestParam(required = true) String address) {
        logger.info("GET /childAlert?address=" + address);
        return this.securityService.searchChildAlert(address);
    }

    @GetMapping(path = "phoneAlert")
    public @ResponseBody List<String> searchPhoneAlert(@RequestParam(required = true) Integer firestation) {
        logger.info("GET /phoneAlert?firestation=" + firestation);
        return this.securityService.searchPhoneAlert(firestation);
    }

    @GetMapping(path = "fire")
    public @ResponseBody List<PersonAddressStationDto> searchFire(@RequestParam(required = true) String address) {
        logger.info("GET /fire?address=" + address);
        return this.securityService.searchFire(address);
    }

    @GetMapping(path = "flood/stations")
    public @ResponseBody List<FloodDto> searchFlood(@RequestParam(required = true) List<Integer> stations) {
        logger.info("GET /flood/stations?<address>=" + stations);
        return this.securityService.searchFlood(stations);
    }


    @GetMapping(path = "personInfo")
    public @ResponseBody List<PersonInfoDto> searchPersonInfo(@RequestParam(required = true) String firstName, String lastName) {
        logger.info("GET /personInfo?firstName=" + firstName + "&+lastName=" + lastName);
        return this.securityService.searchPersonInfo(firstName, lastName);
    }

    @GetMapping(path = "communityEmail")
    public @ResponseBody List<String> searchEmail(@RequestParam(required = true) String city) {
        logger.info("GET /communityEmail?city=" + city);
        return this.securityService.searchEmail(city);
    }


}
