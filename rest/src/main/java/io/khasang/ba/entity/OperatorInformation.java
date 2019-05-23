package io.khasang.ba.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Embeddable class with common information about operator, all field can be nullable and non unique
 */
@Data
@Embeddable
public class OperatorInformation {

    private String fullName;

    private LocalDate birthDate;

    private String country;

    private String city;

    private String about;
}
