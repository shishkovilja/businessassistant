package io.khasang.ba.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Customer entity class. Contains embeddable class {@link CustomerInformation}
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
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

        if (!login.equals(customer.login)) return false;
        if (!registrationDate.equals(customer.registrationDate)) return false;
        if (!password.equals(customer.password)) return false;
        if (!email.equals(customer.email)) return false;
        return customerInformation != null ? customerInformation.equals(customer.customerInformation) : customer.customerInformation == null;

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + registrationDate.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (customerInformation != null ? customerInformation.hashCode() : 0);
        return result;
    }
}
