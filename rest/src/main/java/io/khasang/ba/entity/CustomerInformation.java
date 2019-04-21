package io.khasang.ba.entity;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Embeddable class with common information about user, all field can be nullable and non unique
 */
@Embeddable
public class CustomerInformation {

    private String fullName;

    private LocalDate birthDate;

    private String country;

    private String city;

    private String about;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInformation that = (CustomerInformation) o;
        return Objects.equals(fullName, that.fullName) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(about, that.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, birthDate, country, city, about);
    }
}
