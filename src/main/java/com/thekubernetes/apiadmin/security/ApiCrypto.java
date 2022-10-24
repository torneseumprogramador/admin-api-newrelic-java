package com.thekubernetes.apiadmin.security;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class ApiCrypto {
    public static String encryptToSave(String original) {
        String hashedPW = BCrypt.hashpw(original, BCrypt.gensalt(10));
        return hashedPW;
    }

    public static boolean comparePassword (String password, String hashedPW){
        return BCrypt.checkpw(password, hashedPW);
    }
}
