package io.khasang.ba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * A customer request stage name entity class. Allows to group customer request stages by names, i.e.
 * each {@link CustomerRequestStage} has a relation with this entity.
 */

@Data
@Entity
@Table(name = "customer_request_stage_names")
public class CustomerRequestStageName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    @NaturalId(mutable = true)
    private String name;

    private String description;
}
