package com.doantotnghiep.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "short_description")
    private String short_Description;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long created_by;

    @Column(name = "updated_by")
    private Long updated_by;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Min(value = 1)
    @Max(value = 1)
    @Column(name = "is_active", nullable = false)
    private Integer is_active;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Exercise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Exercise title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_Description() {
        return this.short_Description;
    }

    public Exercise short_Description(String short_Description) {
        this.setShort_Description(short_Description);
        return this;
    }

    public void setShort_Description(String short_Description) {
        this.short_Description = short_Description;
    }

    public String getContent() {
        return this.content;
    }

    public Exercise content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated_by() {
        return this.created_by;
    }

    public Exercise created_by(Long created_by) {
        this.setCreated_by(created_by);
        return this;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public Long getUpdated_by() {
        return this.updated_by;
    }

    public Exercise updated_by(Long updated_by) {
        this.setUpdated_by(updated_by);
        return this;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }

    public String getImage() {
        return this.image;
    }

    public Exercise image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIs_active() {
        return this.is_active;
    }

    public Exercise is_active(Integer is_active) {
        this.setIs_active(is_active);
        return this;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercise)) {
            return false;
        }
        return id != null && id.equals(((Exercise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exercise{" +
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
