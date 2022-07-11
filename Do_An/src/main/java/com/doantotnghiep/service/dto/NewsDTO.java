package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.News} entity.
 */
public class NewsDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String short_Description;

    @NotNull
    private String content;

    private LocalDate created_date;

    @NotNull
    private Long created_by;

    @NotNull
    private String image;

    @NotNull
    @Min(value = 1)
    @Max(value = 1)
    private Integer is_display;

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

    public LocalDate getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIs_display() {
        return is_display;
    }

    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsDTO)) {
            return false;
        }

        NewsDTO newsDTO = (NewsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, newsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", short_Description='" + getShort_Description() + "'" +
            ", content='" + getContent() + "'" +
            ", created_date='" + getCreated_date() + "'" +
            ", created_by=" + getCreated_by() +
            ", image='" + getImage() + "'" +
            ", is_display=" + getIs_display() +
            "}";
    }
}
