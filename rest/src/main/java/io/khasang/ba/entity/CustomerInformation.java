package io.khasang.ba.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Embeddable class with common information about user, all field can be nullable and non unique
 */
@Embeddable
@Data
public class CustomerInformation {

    private String fullName;

    private LocalDate birthDate;

    private String country;

    private String city;

    private String about;
}
