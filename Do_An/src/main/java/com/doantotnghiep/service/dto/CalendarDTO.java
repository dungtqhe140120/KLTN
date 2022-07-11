package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.Calendar} entity.
 */
public class CalendarDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate calendar_date;

    @NotNull
    private Long user_id;

    @NotNull
    private String title;

    private String content;

    @NotNull
    private LocalDate calendar_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCalendar_date() {
        return calendar_date;
    }

    public void setCalendar_date(LocalDate calendar_date) {
        this.calendar_date = calendar_date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCalendar_time() {
        return calendar_time;
    }

    public void setCalendar_time(LocalDate calendar_time) {
        this.calendar_time = calendar_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarDTO)) {
            return false;
        }

        CalendarDTO calendarDTO = (CalendarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calendarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarDTO{" +
            "id=" + getId() +
            ", calendar_date='" + getCalendar_date() + "'" +
            ", user_id=" + getUser_id() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", calendar_time='" + getCalendar_time() + "'" +
            "}";
    }
}
