package io.khasang.ba.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Customer entity class. Contains embeddable class {@link CustomerInformation}
 */
@Entity
@Table(name = "customers")
@Setter
@Getter
@EqualsAndHashCode
public class Customer {

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
    private CustomerInformation customerInformation;
}
