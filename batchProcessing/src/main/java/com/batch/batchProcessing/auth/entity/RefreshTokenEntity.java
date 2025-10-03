package com.batch.batchProcessing.auth.entity;

import com.batch.batchProcessing.admin.entity.Users;
import com.batch.batchProcessing.common.Auditable;
import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="refresh_tokens")
public class RefreshTokenEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false, length = 10000)
    private String refreshToken;

    @Column(name = "revoked")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private Users user;

}
