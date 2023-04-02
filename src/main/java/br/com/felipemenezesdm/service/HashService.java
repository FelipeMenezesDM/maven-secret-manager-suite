package br.com.felipemenezesdm.service;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {
    public String getSHA512(String value) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            for(byte b : md.digest(value.getBytes())) {
                sb.append(String.format("%02x", b));
            }
        }catch(NoSuchAlgorithmException ignore) {}

        return sb.toString();
    }
}
