package com.batch.batchProcessing.security.jwt_auth;

import com.batch.batchProcessing.admin.repository.UsersRepository;
import com.batch.batchProcessing.config.user.UserInfoConfig;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final UsersRepository userInfoRepository;

    public String getUserName(JWTClaimsSet claims) {
        return claims.getSubject();
    }

    public boolean isTokenValid(JWTClaimsSet claims, UserDetails userDetails) {
        final String userName = getUserName(claims);
        boolean isTokenExpired = getIfTokenIsExpired(claims);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        return !isTokenExpired && isTokenUserSameAsDatabase;
    }

    private boolean getIfTokenIsExpired(JWTClaimsSet claims) {
        Date expiration = claims.getExpirationTime();
        return expiration != null && expiration.before(Date.from(Instant.now()));
    }

    public UserDetails userDetails(String email) {
        return userInfoRepository
                .findByEmail(email)
                .map(UserInfoConfig::new)
                .orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + email + " does not exist"));
    }
}