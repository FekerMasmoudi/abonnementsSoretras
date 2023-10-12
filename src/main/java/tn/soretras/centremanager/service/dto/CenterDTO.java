package tn.soretras.centremanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.centremanager.domain.Center} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private Integer deccent;

    private String delcent;

    private String delcentfr;

    private String deadrce;

    private String deobser;

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

    public String getDelcent() {
        return delcent;
    }

    public void setDelcent(String delcent) {
        this.delcent = delcent;
    }

    public String getDelcentfr() {
        return delcentfr;
    }

    public void setDelcentfr(String delcentfr) {
        this.delcentfr = delcentfr;
    }

    public String getDeadrce() {
        return deadrce;
    }

    public void setDeadrce(String deadrce) {
        this.deadrce = deadrce;
    }

    public String getDeobser() {
        return deobser;
    }

    public void setDeobser(String deobser) {
        this.deobser = deobser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterDTO)) {
            return false;
        }

        CenterDTO centerDTO = (CenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterDTO{" +
            "id='" + getId() + "'" +
            ", deccent=" + getDeccent() +
            ", delcent='" + getDelcent() + "'" +
            ", delcentfr='" + getDelcentfr() + "'" +
            ", deadrce='" + getDeadrce() + "'" +
            ", deobser='" + getDeobser() + "'" +
            "}";
    }
}
