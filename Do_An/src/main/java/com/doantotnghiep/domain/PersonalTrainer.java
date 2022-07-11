package com.doantotnghiep.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PersonalTrainer.
 */
@Entity
@Table(name = "personal_trainer")
public class PersonalTrainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "profile_id", nullable = false, unique = true)
    private Long profile_id;

    @Column(name = "experience")
    private String experience;

    @Column(name = "short_description")
    private String short_description;

    @Column(name = "social_account")
    private String socialAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonalTrainer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfile_id() {
        return this.profile_id;
    }

    public PersonalTrainer profile_id(Long profile_id) {
        this.setProfile_id(profile_id);
        return this;
    }

    public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }

    public String getExperience() {
        return this.experience;
    }

    public PersonalTrainer experience(String experience) {
        this.setExperience(experience);
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getShort_description() {
        return this.short_description;
    }

    public PersonalTrainer short_description(String short_description) {
        this.setShort_description(short_description);
        return this;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getSocialAccount() {
        return this.socialAccount;
    }

    public PersonalTrainer socialAccount(String socialAccount) {
        this.setSocialAccount(socialAccount);
        return this;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalTrainer)) {
            return false;
        }
        return id != null && id.equals(((PersonalTrainer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalTrainer{" +
            "id=" + getId() +
            ", profile_id=" + getProfile_id() +
            ", experience='" + getExperience() + "'" +
            ", short_description='" + getShort_description() + "'" +
            ", socialAccount='" + getSocialAccount() + "'" +
            "}";
    }
}
