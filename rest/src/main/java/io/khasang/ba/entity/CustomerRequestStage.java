package io.khasang.ba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A customer request stage entity class. Provides separated stages in order to organize customers' requests processing.
 * Each customer's request should pass a couple of stages (eg. "Accepted" stage, "Started" stage, "Finished" stage etc).<br>
 * This stages "names" are instances of //TODO CustomerRequestStageName
 * Each instance of CustomerRequestStage has <em>@OneToOne</em> relation with //TODO CustomerRequestStageName <br>
 * CustomerRequestStage entity encapsulates a list of {@link Operator} entities assigned to this request stage, i.e.
 * it has <em>@OneToMany</em> relation with <em>Operator</em> entity.
 */

@Data
@Entity
@Table(name = "customer_request_stages")
public class CustomerRequestStage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "creation_timestamp", columnDefinition = "TIMESTAMP")
    @EqualsAndHashCode.Exclude
    private LocalDateTime creationTimestamp;

    @Column(name = "update_timestamp", columnDefinition = "TIMESTAMP")
    @EqualsAndHashCode.Exclude
    private LocalDateTime updateTimestamp;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Operator> operators;

    private String comment;
}
