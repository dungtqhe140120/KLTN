package com.doantotnghiep.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Profile.
 */
@Entity
@Table(name = "jhi_profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "objective")
    private String objective;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "email")
    private String email;

    @Min(value = 1)
    @Max(value = 1)
    @Column(name = "gender")
    private Integer gender;

    @NotNull
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "modified_by")
    private Long modified_by;

    @Column(name = "modified_date")
    private String modified_date;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Profile fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return this.address;
    }

    public Profile address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public Profile phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getObjective() {
        return this.objective;
    }

    public Profile objective(String objective) {
        this.setObjective(objective);
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Profile birthday(LocalDate birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public Profile email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return this.gender;
    }

    public Profile gender(Integer gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public Profile avatar(String avatar) {
        this.setAvatar(avatar);
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getModified_by() {
        return this.modified_by;
    }

    public Profile modified_by(Long modified_by) {
        this.setModified_by(modified_by);
        return this;
    }

    public void setModified_by(Long modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_date() {
        return this.modified_date;
    }

    public Profile modified_date(String modified_date) {
        this.setModified_date(modified_date);
        return this;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", objective='" + getObjective() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", email='" + getEmail() + "'" +
            ", gender=" + getGender() +
            ", avatar='" + getAvatar() + "'" +
            ", modified_by=" + getModified_by() +
            ", modified_date='" + getModified_date() + "'" +
            "}";
    }
}
