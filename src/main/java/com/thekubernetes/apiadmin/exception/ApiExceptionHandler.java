package com.thekubernetes.apiadmin.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiExceptionHandler {

    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e ){

        HttpStatus badRequesStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),
                                                     e, 
                                                     HttpStatus.BAD_REQUEST, 
                                                     ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

       return new ResponseEntity<>(apiException,badRequesStatus);                                            

    }
    
}
