package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.TraineePT} entity.
 */
public class TraineePTDTO implements Serializable {

    private Long id;

    @NotNull
    private Long personalTrainer_id;

    @NotNull
    private Long trainee_id;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonalTrainer_id() {
        return personalTrainer_id;
    }

    public void setPersonalTrainer_id(Long personalTrainer_id) {
        this.personalTrainer_id = personalTrainer_id;
    }

    public Long getTrainee_id() {
        return trainee_id;
    }

    public void setTrainee_id(Long trainee_id) {
        this.trainee_id = trainee_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineePTDTO)) {
            return false;
        }

        TraineePTDTO traineePTDTO = (TraineePTDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traineePTDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineePTDTO{" +
            "id=" + getId() +
            ", personalTrainer_id=" + getPersonalTrainer_id() +
            ", trainee_id=" + getTrainee_id() +
            ", type='" + getType() + "'" +
            "}";
    }
}
