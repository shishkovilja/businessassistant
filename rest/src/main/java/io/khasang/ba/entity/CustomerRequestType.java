package io.khasang.ba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Customer Request Type entity class. Provides request types specifications, each type has name, description
 * and {@link CustomerRequestCategory}.
 * Each request type must have unique name, which should not be changed after it's creation (via PUT requests).
 * For this purpose, {@link NaturalId} annotations is used
 */

@Data
@Entity
@Table(name = "operator_roles")
public class CustomerRequestType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    @NaturalId
    private String name;

    private String description;

    @NotNull
    @Column(name = "request_category")
    private CustomerRequestCategory customerRequestCategory;
}
