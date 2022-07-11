package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.Exercise} entity.
 */
public class ExerciseDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String short_Description;

    @NotNull
    private String content;

    @NotNull
    private Long created_by;

    private Long updated_by;

    @NotNull
    private String image;

    @NotNull
    @Min(value = 1)
    @Max(value = 1)
    private Integer is_active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_Description() {
        return short_Description;
    }

    public void setShort_Description(String short_Description) {
        this.short_Description = short_Description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExerciseDTO)) {
            return false;
        }

        ExerciseDTO exerciseDTO = (ExerciseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exerciseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExerciseDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", short_Description='" + getShort_Description() + "'" +
            ", content='" + getContent() + "'" +
            ", created_by=" + getCreated_by() +
            ", updated_by=" + getUpdated_by() +
            ", image='" + getImage() + "'" +
            ", is_active=" + getIs_active() +
            "}";
    }
}
