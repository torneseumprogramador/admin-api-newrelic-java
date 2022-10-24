package com.thekubernetes.apiadmin.service;
import org.springframework.http.ResponseEntity;

import com.thekubernetes.apiadmin.security.Token;

public interface ITokenService {
    public ResponseEntity<Object> ValidToken(Token token);
}
