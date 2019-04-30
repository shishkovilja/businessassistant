package io.khasang.ba.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Customer entity class. Contains embeddable class {@link CustomerInformation}
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @NaturalId
    private String login;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationTimestamp;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @Embedded
    private CustomerInformation customerInformation;

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

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(login, customer.login) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(customerInformation, customer.customerInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email, customerInformation);
    }
}
