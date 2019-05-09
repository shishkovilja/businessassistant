package io.khasang.ba.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Embeddable class with common information about user, all field can be nullable and non unique
 */
@Embeddable
@Setter
@Getter
@EqualsAndHashCode
public class CustomerInformation {

    private String fullName;

    private LocalDate birthDate;

    private String country;

    private String city;

    private String about;
}
