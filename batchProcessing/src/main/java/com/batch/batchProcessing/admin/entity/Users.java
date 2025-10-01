package com.batch.batchProcessing.admin.entity;

/*
 * @author Pasang Gelbu Sherpa *
 * @created 6/6/2024 at 11:28 PM
 */

import com.batch.batchProcessing.role.entity.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;
    private String email;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private Long age;
    private String profilePic;
    private String password;
    private boolean isVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<Roles> roles = new ArrayList<>();
}
