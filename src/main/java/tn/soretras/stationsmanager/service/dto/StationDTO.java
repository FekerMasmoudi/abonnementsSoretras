package tn.soretras.stationsmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.stationsmanager.domain.Station} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StationDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String namefr;

    @NotNull(message = "must not be null")
    private String namear;

    private String longitude;

    private String lattitude;

    @NotNull(message = "must not be null")
    private String decstat;

    @NotNull(message = "must not be null")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamefr() {
        return namefr;
    }

    public void setNamefr(String namefr) {
        this.namefr = namefr;
    }

    public String getNamear() {
        return namear;
    }

    public void setNamear(String namear) {
        this.namear = namear;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getDecstat() {
        return decstat;
    }

    public void setDecstat(String decstat) {
        this.decstat = decstat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StationDTO)) {
            return false;
        }

        StationDTO stationDTO = (StationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StationDTO{" +
            "id='" + getId() + "'" +
            ", namefr='" + getNamefr() + "'" +
            ", namear='" + getNamear() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", lattitude='" + getLattitude() + "'" +
            ", decstat='" + getDecstat() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
