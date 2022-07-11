package com.doantotnghiep.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "notification_time", nullable = false)
    private LocalDate notification_time;

    @NotNull
    @Column(name = "sender_id", nullable = false)
    private Long sender_id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Notification content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getNotification_time() {
        return this.notification_time;
    }

    public Notification notification_time(LocalDate notification_time) {
        this.setNotification_time(notification_time);
        return this;
    }

    public void setNotification_time(LocalDate notification_time) {
        this.notification_time = notification_time;
    }

    public Long getSender_id() {
        return this.sender_id;
    }

    public Notification sender_id(Long sender_id) {
        this.setSender_id(sender_id);
        return this;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", notification_time='" + getNotification_time() + "'" +
            ", sender_id=" + getSender_id() +
            "}";
    }
}
