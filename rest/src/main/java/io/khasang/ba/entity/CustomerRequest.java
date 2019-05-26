package io.khasang.ba.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * This domain entity class is purposed to provide possibility to each customer to submit requests for some type of service
 */
@Data
@Entity
@Table(name = "customer_requests")
public class CustomerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
