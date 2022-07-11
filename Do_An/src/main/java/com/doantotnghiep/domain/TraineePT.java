package com.doantotnghiep.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TraineePT.
 */
@Entity
@Table(name = "traineept")
public class TraineePT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "personal_trainer_id", nullable = false)
    private Long personalTrainer_id;

    @NotNull
    @Column(name = "trainee_id", nullable = false)
    private Long trainee_id;

    @Column(name = "jhi_type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TraineePT id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonalTrainer_id() {
        return this.personalTrainer_id;
    }

    public TraineePT personalTrainer_id(Long personalTrainer_id) {
        this.setPersonalTrainer_id(personalTrainer_id);
        return this;
    }

    public void setPersonalTrainer_id(Long personalTrainer_id) {
        this.personalTrainer_id = personalTrainer_id;
    }

    public Long getTrainee_id() {
        return this.trainee_id;
    }

    public TraineePT trainee_id(Long trainee_id) {
        this.setTrainee_id(trainee_id);
        return this;
    }

    public void setTrainee_id(Long trainee_id) {
        this.trainee_id = trainee_id;
    }

    public String getType() {
        return this.type;
    }

    public TraineePT type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineePT)) {
            return false;
        }
        return id != null && id.equals(((TraineePT) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineePT{" +
            "id=" + getId() +
            ", personalTrainer_id=" + getPersonalTrainer_id() +
            ", trainee_id=" + getTrainee_id() +
            ", type='" + getType() + "'" +
            "}";
    }
}
