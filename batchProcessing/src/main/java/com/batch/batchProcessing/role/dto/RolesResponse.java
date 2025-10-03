package com.batch.batchProcessing.role.dto;

import com.batch.batchProcessing.role.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponse {
    private String role;
    private String description;


    public RolesResponse(Roles roles) {
        this.role = roles.getName();
        this.description = roles.getDescription();
    }
}
