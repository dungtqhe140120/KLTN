package com.doantotnghiep.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TraineeExercise.
 */
@Entity
@Table(name = "trainee_exercise")
public class TraineeExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "trainee_id", nullable = false)
    private Long trainee_id;

    @NotNull
    @Column(name = "exercise_id", nullable = false)
    private Long exercise_id;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long created_by;

    @Column(name = "created_date")
    private LocalDate created_Date;

    @NotNull
    @Column(name = "modified_by", nullable = false)
    private Long modified_by;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private LocalDate modified_date;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TraineeExercise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainee_id() {
        return this.trainee_id;
    }

    public TraineeExercise trainee_id(Long trainee_id) {
        this.setTrainee_id(trainee_id);
        return this;
    }

    public void setTrainee_id(Long trainee_id) {
        this.trainee_id = trainee_id;
    }

    public Long getExercise_id() {
        return this.exercise_id;
    }

    public TraineeExercise exercise_id(Long exercise_id) {
        this.setExercise_id(exercise_id);
        return this;
    }

    public void setExercise_id(Long exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getNote() {
        return this.note;
    }

    public TraineeExercise note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCreated_by() {
        return this.created_by;
    }

    public TraineeExercise created_by(Long created_by) {
        this.setCreated_by(created_by);
        return this;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public LocalDate getCreated_Date() {
        return this.created_Date;
    }

    public TraineeExercise created_Date(LocalDate created_Date) {
        this.setCreated_Date(created_Date);
        return this;
    }

    public void setCreated_Date(LocalDate created_Date) {
        this.created_Date = created_Date;
    }

    public Long getModified_by() {
        return this.modified_by;
    }

    public TraineeExercise modified_by(Long modified_by) {
        this.setModified_by(modified_by);
        return this;
    }

    public void setModified_by(Long modified_by) {
        this.modified_by = modified_by;
    }

    public LocalDate getModified_date() {
        return this.modified_date;
    }

    public TraineeExercise modified_date(LocalDate modified_date) {
        this.setModified_date(modified_date);
        return this;
    }

    public void setModified_date(LocalDate modified_date) {
        this.modified_date = modified_date;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineeExercise)) {
            return false;
        }
        return id != null && id.equals(((TraineeExercise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineeExercise{" +
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
