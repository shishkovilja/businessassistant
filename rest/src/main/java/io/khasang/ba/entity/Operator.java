package io.khasang.ba.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Operator entity class. Operators in are users, handling customers' requests
 */
@Entity
@Table(name = "operators")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @NaturalId
    private String login;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationTimestamp;

    @NotEmpty
    @Column(unique = true)
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @Embedded
    private OperatorInformation operatorInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(LocalDateTime registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OperatorInformation getOperatorInformation() {
        return operatorInformation;
    }

    public void setOperatorInformation(OperatorInformation operatorInformation) {
        this.operatorInformation = operatorInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(login, operator.login) &&
                Objects.equals(password, operator.password) &&
                Objects.equals(email, operator.email) &&
                Objects.equals(operatorInformation, operator.operatorInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email, operatorInformation);
    }
}
