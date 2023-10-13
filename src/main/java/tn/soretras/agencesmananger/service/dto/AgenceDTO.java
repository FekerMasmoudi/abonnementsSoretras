package tn.soretras.agencesmananger.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.agencesmananger.domain.Agence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenceDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private Integer deccent;

    @NotNull(message = "must not be null")
    private Integer decagenc;

    private String delagenc;

    private String delagencfr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeccent() {
        return deccent;
    }

    public void setDeccent(Integer deccent) {
        this.deccent = deccent;
    }

    public Integer getDecagenc() {
        return decagenc;
    }

    public void setDecagenc(Integer decagenc) {
        this.decagenc = decagenc;
    }

    public String getDelagenc() {
        return delagenc;
    }

    public void setDelagenc(String delagenc) {
        this.delagenc = delagenc;
    }

    public String getDelagencfr() {
        return delagencfr;
    }

    public void setDelagencfr(String delagencfr) {
        this.delagencfr = delagencfr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenceDTO)) {
            return false;
        }

        AgenceDTO agenceDTO = (AgenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceDTO{" +
            "id='" + getId() + "'" +
            ", deccent=" + getDeccent() +
            ", decagenc=" + getDecagenc() +
            ", delagenc='" + getDelagenc() + "'" +
            ", delagencfr='" + getDelagencfr() + "'" +
            "}";
    }
}
