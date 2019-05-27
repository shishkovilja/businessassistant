package io.khasang.ba.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.khasang.ba.entity.embeddable.Address;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * POI - Point of Interest. This is object include any params i.e. address, geographical coordinates,
 * include category etc.
 */
@Entity
@Table(name = "points_of_interest")
@Data
@NoArgsConstructor
public class PointOfInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ColumnDefault(value = "'unknown'")
    private String category;

    @Column(name = "start_work", columnDefinition = "TIME")
    @ColumnDefault(value = "'00:00:00'")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startWork;

    @ColumnDefault(value = "0")
    @Column(name = "work_time")
    private Integer workTime;

    @Embedded
    private Address address;
}
