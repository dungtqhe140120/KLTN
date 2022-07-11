package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.Trainee} entity.
 */
public class TraineeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long profile_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineeDTO)) {
            return false;
        }

        TraineeDTO traineeDTO = (TraineeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traineeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineeDTO{" +
            "id=" + getId() +
            ", profile_id=" + getProfile_id() +
            "}";
    }
}
