package io.khasang.ba.entity;

import javax.persistence.*;

/**
 * Customer entity class
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
}
