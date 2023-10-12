package tn.soretras.stationsmanager.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Station.
 */
@Document(collection = "station")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("namefr")
    private String namefr;

    @NotNull(message = "must not be null")
    @Field("namear")
    private String namear;

    @Field("longitude")
    private String longitude;

    @Field("lattitude")
    private String lattitude;

    @NotNull(message = "must not be null")
    @Field("decstat")
    private String decstat;

    @NotNull(message = "must not be null")
    @Field("status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Station id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamefr() {
        return this.namefr;
    }

    public Station namefr(String namefr) {
        this.setNamefr(namefr);
        return this;
    }

    public void setNamefr(String namefr) {
        this.namefr = namefr;
    }

    public String getNamear() {
        return this.namear;
    }

    public Station namear(String namear) {
        this.setNamear(namear);
        return this;
    }

    public void setNamear(String namear) {
        this.namear = namear;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Station longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return this.lattitude;
    }

    public Station lattitude(String lattitude) {
        this.setLattitude(lattitude);
        return this;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getDecstat() {
        return this.decstat;
    }

    public Station decstat(String decstat) {
        this.setDecstat(decstat);
        return this;
    }

    public void setDecstat(String decstat) {
        this.decstat = decstat;
    }

    public String getStatus() {
        return this.status;
    }

    public Station status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        return id != null && id.equals(((Station) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", namefr='" + getNamefr() + "'" +
            ", namear='" + getNamear() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", lattitude='" + getLattitude() + "'" +
            ", decstat='" + getDecstat() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
