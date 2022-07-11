package com.doantotnghiep.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A News.
 */
@Entity
@Table(name = "news")
public class News implements Serializable {

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

    @Column(name = "created_date")
    private LocalDate created_date;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long created_by;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Min(value = 1)
    @Max(value = 1)
    @Column(name = "is_display", nullable = false)
    private Integer is_display;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public News id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public News title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_Description() {
        return this.short_Description;
    }

    public News short_Description(String short_Description) {
        this.setShort_Description(short_Description);
        return this;
    }

    public void setShort_Description(String short_Description) {
        this.short_Description = short_Description;
    }

    public String getContent() {
        return this.content;
    }

    public News content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated_date() {
        return this.created_date;
    }

    public News created_date(LocalDate created_date) {
        this.setCreated_date(created_date);
        return this;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public Long getCreated_by() {
        return this.created_by;
    }

    public News created_by(Long created_by) {
        this.setCreated_by(created_by);
        return this;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public String getImage() {
        return this.image;
    }

    public News image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIs_display() {
        return this.is_display;
    }

    public News is_display(Integer is_display) {
        this.setIs_display(is_display);
        return this;
    }

    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        return id != null && id.equals(((News) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "News{" +
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
