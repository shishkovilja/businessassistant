package io.khasang.ba.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

/**
 * Role entity class. Provides spring security roles in "Business Assistant" project.
 * Each role must have unique name
 */
@Entity
@Table(name = "roles")
public class OperatorRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorRole operatorRole = (OperatorRole) o;
        return Objects.equals(name, operatorRole.name) &&
                Objects.equals(description, operatorRole.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
