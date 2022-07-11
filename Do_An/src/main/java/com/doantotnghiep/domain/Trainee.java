package com.doantotnghiep.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Trainee.
 */
@Entity
@Table(name = "trainee")
public class Trainee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "profile_id", nullable = false)
    private Long profile_id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trainee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfile_id() {
        return this.profile_id;
    }

    public Trainee profile_id(Long profile_id) {
        this.setProfile_id(profile_id);
        return this;
    }

    public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trainee)) {
            return false;
        }
        return id != null && id.equals(((Trainee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trainee{" +
            "id=" + getId() +
            ", profile_id=" + getProfile_id() +
            "}";
    }
}
