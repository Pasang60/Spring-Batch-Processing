/**
 * Author: Pasang Gelbu Sherpa
 * Device: Victus
 * Date: 2024-10-10
 * Time: 14:30
 * Project: batchProcessing
 */

package com.batch.batchProcessing.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    private String message;
    private String email;
    private LocalDateTime expiresAt;

}
