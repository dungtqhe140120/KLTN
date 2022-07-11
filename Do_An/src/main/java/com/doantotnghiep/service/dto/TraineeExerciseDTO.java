package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.TraineeExercise} entity.
 */
public class TraineeExerciseDTO implements Serializable {

    private Long id;

    @NotNull
    private Long trainee_id;

    @NotNull
    private Long exercise_id;

    private String note;

    @NotNull
    private Long created_by;

    private LocalDate created_Date;

    @NotNull
    private Long modified_by;

    @NotNull
    private LocalDate modified_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainee_id() {
        return trainee_id;
    }

    public void setTrainee_id(Long trainee_id) {
        this.trainee_id = trainee_id;
    }

    public Long getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(Long exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public LocalDate getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(LocalDate created_Date) {
        this.created_Date = created_Date;
    }

    public Long getModified_by() {
        return modified_by;
    }

    public void setModified_by(Long modified_by) {
        this.modified_by = modified_by;
    }

    public LocalDate getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDate modified_date) {
        this.modified_date = modified_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineeExerciseDTO)) {
            return false;
        }

        TraineeExerciseDTO traineeExerciseDTO = (TraineeExerciseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traineeExerciseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineeExerciseDTO{" +
            "id=" + getId() +
            ", trainee_id=" + getTrainee_id() +
            ", exercise_id=" + getExercise_id() +
            ", note='" + getNote() + "'" +
            ", created_by=" + getCreated_by() +
            ", created_Date='" + getCreated_Date() + "'" +
            ", modified_by=" + getModified_by() +
            ", modified_date='" + getModified_date() + "'" +
            "}";
    }
}
