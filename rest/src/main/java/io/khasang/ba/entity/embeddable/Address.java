package io.khasang.ba.entity.embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;

/**
 * This class storages address something
 * minimal requariment on input is City, Street and Build.
 */

@Embeddable
@Data
@NoArgsConstructor
public class Address {
    private String region;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    private String postcode;

    @NotBlank
    private String build;

    private String room;

    @ColumnDefault(value = "0.000000")
    private Double latitude;

    @ColumnDefault(value = "0.000000")
    private Double longitude;
}
