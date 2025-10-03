package com.batch.batchProcessing.security.jwt_auth;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenGenerator {


    public String generateAccessToken(Authentication authentication) {
        log.info("[JwtTokenGenerator:generateAccessToken] Token Creation Started for: {}", authentication.getName());

        String roles = getRolesOfUser(authentication);
        String permissions = getPermissionsFromRoles(roles);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("CollegeHub")
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(Instant.now().plus(150, ChronoUnit.MINUTES)))
                .subject(authentication.getName())
                .claim("scope", permissions)
                .build();

        JWEObject jweObject = new JWEObject(
                new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM),
                new Payload(claims.toJSONObject())
        );


        String token = jweObject.serialize();
        log.info("[JwtTokenGenerator:generateAccessToken] Generated encrypted access token for: {}", authentication.getName());
        return token;
    }

    public String generateRefreshToken(Authentication authentication) {
        log.info("[JwtTokenGenerator:generateRefreshToken] Token Creation Started for: {}", authentication.getName());

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("CollegeHub")
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .subject(authentication.getName())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        JWEObject jweObject = new JWEObject(
                new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM),
                new Payload(claims.toJSONObject())
        );


        String token = jweObject.serialize();
        log.info("[JwtTokenGenerator:generateRefreshToken] Generated encrypted refresh token for: {}", authentication.getName());
        return token;
    }

    private static String getRolesOfUser(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

    private String getPermissionsFromRoles(String roles) {
        Set<String> permissions = new HashSet<>();
        if (roles.contains("ROLE_SCHOOL_ADMIN")) {
            permissions.addAll(List.of("READ", "WRITE", "DELETE"));
        }
        if (roles.contains("ROLE_USER")) {
            permissions.add("READ");
        }
        return String.join(" ", permissions);
    }
}