package com.doantotnghiep.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.doantotnghiep.domain.Admin} entity.
 */
public class AdminDTO implements Serializable {

    private Long id;

    @NotNull
    private Long profile_id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminDTO)) {
            return false;
        }

        AdminDTO adminDTO = (AdminDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminDTO{" +
            "id=" + getId() +
            ", profile_id=" + getProfile_id() +
            "}";
    }
}
