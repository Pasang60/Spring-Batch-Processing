package com.batch.batchProcessing.student.entity;

/*
 * @author Pasang Gelbu Sherpa *
 * @created 6/6/2024 at 11:28 PM
 */

import com.batch.batchProcessing.common.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String degreeName;

    private LocalDate enrolledYear;

    private Long duration;

    private BigDecimal fee;

    private String schoolOrCollegeName;

    @Column(nullable = false)
    private String status;

}