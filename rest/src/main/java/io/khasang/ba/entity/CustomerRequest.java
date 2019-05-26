package io.khasang.ba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * This domain entity class is purposed to provide possibility for each customer to submit requests for some type of service
 */
@Data
@Entity
@Table(name = "customer_requests")
public class CustomerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String comment;
}
