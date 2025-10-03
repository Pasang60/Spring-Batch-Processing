/**
 * Author: Pasang Gelbu Sherpa
 * User:VICTUS
 * Date: 2024-06-11
 * Time: 15:28
 */

package com.batch.batchProcessing.auth.messages;

public class AuthExceptionMessages {
    public static final String INVALID_OTP = "Invalid OTP";

    private AuthExceptionMessages(){}
    public static final String USER_NOT_FOUND = "User not found with email: ";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String REFRESH_TOKEN_MISSING = "Refresh token is missing";
    public static final String REFRESH_TOKEN_REVOKED = "Refresh token revoked";
    public static final String TRY_AGAIN = "Please Try Again";
}
