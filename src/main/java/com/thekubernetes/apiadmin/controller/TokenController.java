package com.thekubernetes.apiadmin.controller;

import com.thekubernetes.apiadmin.security.Token;
import com.thekubernetes.apiadmin.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private ITokenService service;

    @PostMapping("/token/valid")
    public ResponseEntity<Object> createAdmin(@RequestBody Token token) {
        return service.ValidToken(token);
    }   
}
