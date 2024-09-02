package com.example.RentCar.Services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.RentCar.DTOS.UserRequestDTO;
import com.example.RentCar.Models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtServicesImpl implements JwtServices {
    private UserRequestDTO userRequestDTO;
    // private final UserRepository userRepository;
    // @Value("${token.signing.key}")
    private String jwtSigningKey = "QD9GSHIrS2JiUGVTaFY2TXk3MzM2Njc5OTI0NDIyNjUyOTQ4NDA0RDYzNTE=";

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public boolean isTokenValid(String token, UserServicesImpl userServices) {
        final String email = extractUserName(token);
        UserDetails user = (userServices.loadUserByUsername(email));
        String email2 = user.getUsername();
        return (email.equals(email2) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        System.out.println(claims);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getUserEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private byte[] hexStringToByteArray(String s) {
        // Remove spaces from the input string
        s = s.replaceAll("\\s+", "");

        // Use Decoders.BASE64URL.decode to handle base64 decoding
        return Decoders.BASE64URL.decode(s);
    }

    private Key getSigningKey() {
        byte[] keyBytes = hexStringToByteArray(jwtSigningKey);
        System.out.println(keyBytes);

        // Encode to Base64
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Base64 Endcoded Key: " + base64EncodedKey);

        // Decode from Base64
        byte[] decodedKeyBytes = Base64.getDecoder().decode(base64EncodedKey);
        System.out.println(decodedKeyBytes);

        return new SecretKeySpec(decodedKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
