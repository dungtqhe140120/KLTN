package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.PersonalTrainer} entity.
 */
public class PersonalTrainerDTO implements Serializable {

    private Long id;

    @NotNull
    private Long profile_id;

    private String experience;

    private String short_description;

    private String socialAccount;

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalTrainerDTO)) {
            return false;
        }

        PersonalTrainerDTO personalTrainerDTO = (PersonalTrainerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalTrainerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalTrainerDTO{" +
            "id=" + getId() +
            ", profile_id=" + getProfile_id() +
            ", experience='" + getExperience() + "'" +
            ", short_description='" + getShort_description() + "'" +
            ", socialAccount='" + getSocialAccount() + "'" +
            "}";
    }
}
