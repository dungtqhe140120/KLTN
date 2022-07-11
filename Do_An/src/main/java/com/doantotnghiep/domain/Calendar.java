package com.doantotnghiep.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "calendar_date", nullable = false)
    private LocalDate calendar_date;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "calendar_time", nullable = false)
    private LocalDate calendar_time;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Calendar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCalendar_date() {
        return this.calendar_date;
    }

    public Calendar calendar_date(LocalDate calendar_date) {
        this.setCalendar_date(calendar_date);
        return this;
    }

    public void setCalendar_date(LocalDate calendar_date) {
        this.calendar_date = calendar_date;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public Calendar user_id(Long user_id) {
        this.setUser_id(user_id);
        return this;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return this.title;
    }

    public Calendar title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Calendar content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCalendar_time() {
        return this.calendar_time;
    }

    public Calendar calendar_time(LocalDate calendar_time) {
        this.setCalendar_time(calendar_time);
        return this;
    }

    public void setCalendar_time(LocalDate calendar_time) {
        this.calendar_time = calendar_time;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendar)) {
            return false;
        }
        return id != null && id.equals(((Calendar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", calendar_date='" + getCalendar_date() + "'" +
            ", user_id=" + getUser_id() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", calendar_time='" + getCalendar_time() + "'" +
            "}";
    }
}
