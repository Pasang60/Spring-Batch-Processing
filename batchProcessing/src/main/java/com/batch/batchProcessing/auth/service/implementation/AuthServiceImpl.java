/**
 * Author: Pasang Gelbu Sherpa
 * User:VICTUS
 * Date: 2024-06-11
 * Time: 15:28
 */

package com.batch.batchProcessing.auth.service.implementation;

import com.batch.batchProcessing.admin.entity.Users;
import com.batch.batchProcessing.admin.repository.UsersRepository;
import com.batch.batchProcessing.auth.dto.TokenType;
import com.batch.batchProcessing.auth.dto.request.AuthRequest;
import com.batch.batchProcessing.auth.dto.request.forgot_password.ResetPasswordRequest;
import com.batch.batchProcessing.auth.dto.response.AuthResponse;
import com.batch.batchProcessing.auth.dto.response.ForgotPasswordResponse;
import com.batch.batchProcessing.auth.entity.RefreshTokenEntity;
import com.batch.batchProcessing.auth.messages.AuthExceptionMessages;
import com.batch.batchProcessing.auth.messages.AuthLogMessages;
import com.batch.batchProcessing.auth.messages.AuthResponseMessages;
import com.batch.batchProcessing.auth.repository.RefreshTokenRepo;
import com.batch.batchProcessing.auth.service.AuthService;
import com.batch.batchProcessing.role.dto.RolesResponse;
import com.batch.batchProcessing.role.entity.Roles;
import com.batch.batchProcessing.security.jwt_auth.JwtTokenGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UsersRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;
    private final AuthenticationManager authenticationManager;
//    private final OTPService otpService;
//    private final MailService mailService;
//    private final EncryptionUtil encryptionUtil;


    @Override
    public AuthResponse getJwtTokensAfterAuthentication(AuthRequest authenticationRequest, HttpServletResponse response) {
        try {
            // Authenticate the user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.email(),
                            authenticationRequest.password()
                    )
            );

            // Fetch user information after successful authentication
            var userInfoEntity = userRepository.findByEmail(authenticationRequest.email())
                    .orElseThrow(() -> {
                        log.error(AuthLogMessages.USER_NOT_FOUND, authenticationRequest.email());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");                    });
            String fullName = userInfoEntity.getFullName();

            // Generate JWT tokens
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            // Save refresh token and create cookie
            saveUserRefreshToken(userInfoEntity, refreshToken);
            createRefreshTokenCookie(response, refreshToken);


            log.info(AuthLogMessages.ACCESS_TOKEN_GENERATED, userInfoEntity.getEmail());


            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userRole(userInfoEntity.getRoles().stream().map(RolesResponse::new).toList())
                    .tokenType(TokenType.Bearer)
                    .fullName(fullName)
                    .build();

        } catch (AuthenticationException e) {
            log.error(AuthLogMessages.INVALID_CREDENTIALS, authenticationRequest.email());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AuthExceptionMessages.INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error(AuthLogMessages.EXCEPTION_AUTHENTICATING, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, AuthExceptionMessages.TRY_AGAIN);
        }
    }

    private void saveUserRefreshToken(Users users, String refreshToken) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .user(users)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
    }

    @Override
    public AuthResponse getAccessTokenUsingRefreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AuthExceptionMessages.REFRESH_TOKEN_MISSING);
        }

        // Validate refresh token from database
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, AuthExceptionMessages.REFRESH_TOKEN_REVOKED));

        Users users = refreshTokenEntity.getUser();

        // Generate new access token
        Authentication authentication = createAuthenticationObject(users);
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        log.info(AuthLogMessages.ACCESS_TOKEN_GENERATED, users.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .tokenType(TokenType.Bearer)
                .build();
    }

    private static Authentication createAuthenticationObject(Users users) {
        // Extract user details from UserDetailsEntity
        String username = users.getEmail();
        String password = users.getPassword();
        List<Roles> roles = users.getRoles();

        // Extract authorities from roles
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) role::getName)
                .toList();

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }


    @Override
    public ForgotPasswordResponse forgotPassword(String email) {
        //Users users = userInfoRepository.findByEmailId(email)
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AuthExceptionMessages.USER_NOT_FOUND + email));
        //OTP otp = otpService.saveOTP(users, OTPPurpose.FORGOT_PASSWORD);
        //String forgotPasswordLink = frontEndUrl + forgotPasswordUrl + "?token=" + encryptionUtil.encryptOtp(otp.getOtpValue());
        //mailService.sendForgotPasswordMail(users, forgotPasswordLink, otp.getExpiryTime());
        //return new ForgotPasswordResponse(AuthResponseMessages.PASSWORD_RESET_LINK_SENT, users.getEmailId(), otp.getExpiryTime());
        return null;
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
//        OTP otp = otpService.getOTP(encryptionUtil.decryptOtp(resetPasswordRequest.getOtp()), OTPPurpose.FORGOT_PASSWORD);
//
//        Users user = userInfoRepository.findByEmailId(otp.getUser().getEmailId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AuthExceptionMessages.USER_NOT_FOUND + otp.getUser().getEmailId()));
//        otpService.validateOTP(user, otp.getOtpValue());
//        user.setPassword(new BCryptPasswordEncoder().encode(resetPasswordRequest.getNewPassword()));
//        userInfoRepository.save(user);
//        // Log the reset password activity
//        logUserActivity(user, "Reset Password", "User password reset successfully.");
//        return AuthResponseMessages.PASSWORD_RESET_SUCCESS;
        return null;
    }
}