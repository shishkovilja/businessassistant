package io.khasang.ba.entity.embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ColumnDefault;

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

    @NonNull
    private String city;

    @NonNull
    private String street;

    private String postcode;

    @NonNull
    private String build;

    @NonNull
    private String room;

    @ColumnDefault(value = "0.000000")
    private Double latitude;

    @ColumnDefault(value = "0.000000")
    private Double longitude;
}
