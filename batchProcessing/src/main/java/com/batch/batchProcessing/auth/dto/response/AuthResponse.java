package com.batch.batchProcessing.auth.dto.response;

import com.batch.batchProcessing.auth.dto.TokenType;
import com.batch.batchProcessing.role.dto.RolesResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;

    @JsonProperty("token_type")
    private TokenType tokenType;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("user_role")
    private List<RolesResponse> userRole;

}