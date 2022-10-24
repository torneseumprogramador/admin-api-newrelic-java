package com.thekubernetes.apiadmin.service;

import com.thekubernetes.apiadmin.security.Token;
import com.thekubernetes.apiadmin.security.TokenUtil;
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements ITokenService {

    @Override
    public ResponseEntity<Object> ValidToken(Token token) {
        String tokenString = token.getToken();
        if (token.getToken() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'token' precisa ser informado.\"}");
      }
      try {
        Authentication validacao = TokenUtil.validateTokenUtil(tokenString);
          return ResponseEntity.status(201).body(validacao);
      } catch (Exception e) {
        return ResponseEntity.status(403).body("{\"message\":\"Não autorizado\"}");
      }
    }

    

}
