package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.dto.ChildDto;
import com.ocrooms.safetynet.dto.PersonListDto;
import com.ocrooms.safetynet.service.SecurityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(path = "firestation")
    public @ResponseBody PersonListDto searchFirestation(@RequestParam(required = true) Integer station) {
        return this.securityService.searchFirestation(station);
    }


    @GetMapping(path = "childAlert")
    public @ResponseBody List<ChildDto> searchChildAlert(@RequestParam(required = true) String address) {
        return this.securityService.searchChildAlert(address);
    }


}
