/**
 * Author: Pasang Gelbu Sherpa
 * User:VICTUS
 * Date: 2024-06-11
 * Time: 15:28
 */

package com.batch.batchProcessing.auth.messages;

public class AuthLogMessages {
    private AuthLogMessages() {}
    public static final String USER_NOT_FOUND = "[AuthService:userSignInAuth] User :{} not found";
    public static final String INVALID_CREDENTIALS = "[AuthService:userSignInAuth] Invalid credentials for user: {}";
    public static final String ACCESS_TOKEN_GENERATED = "[AuthService:userSignInAuth] Access token for user:{}, has been generated";
    public static final String EXCEPTION_AUTHENTICATING = "[AuthService:userSignInAuth] Exception while authenticating the user due to : {}";

}
