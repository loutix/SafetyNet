package com.ocrooms.safetynet.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiException {
    private final Date timeStamp;
    private final String message;
    private  final String details;
}
