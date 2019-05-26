package io.khasang.ba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Operator entity class. Operators are users, handling customers' requests
 */
@Data
@Entity
@Table(name = "operators")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    @NaturalId
    private String login;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    @EqualsAndHashCode.Exclude
    private LocalDateTime registrationTimestamp;

    @NotBlank
    private String password;

    @NotBlank
    @Column(unique = true)
    private String email;

    @Embedded
    private OperatorInformation operatorInformation;
}
