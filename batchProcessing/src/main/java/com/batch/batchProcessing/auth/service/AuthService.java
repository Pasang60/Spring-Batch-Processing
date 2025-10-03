package com.batch.batchProcessing.auth.service;


import com.batch.batchProcessing.auth.dto.request.AuthRequest;
import com.batch.batchProcessing.auth.dto.request.forgot_password.ResetPasswordRequest;
import com.batch.batchProcessing.auth.dto.response.AuthResponse;
import com.batch.batchProcessing.auth.dto.response.ForgotPasswordResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;

public interface AuthService {


    AuthResponse getJwtTokensAfterAuthentication(AuthRequest authenticationRequest, HttpServletResponse response);

    AuthResponse getAccessTokenUsingRefreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken);

    ForgotPasswordResponse forgotPassword(String email);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);
}