package tn.soretras.itineraires.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.itineraires.domain.Itineraire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItineraireDTO implements Serializable {

    private String id;

    private Integer deccent;

    @NotNull(message = "must not be null")
    private Integer decagenc;

    @NotNull(message = "must not be null")
    private String denumli;

    @NotNull(message = "must not be null")
    private String decstat;

    @NotNull(message = "must not be null")
    private Integer denumlg;

    private Integer dekmsta;

    private Integer dedurtr;

    private Integer deescale;

    private String embra;

    private Integer section;

    private String sens;

    private String dectyst;

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

    public String getDenumli() {
        return denumli;
    }

    public void setDenumli(String denumli) {
        this.denumli = denumli;
    }

    public String getDecstat() {
        return decstat;
    }

    public void setDecstat(String decstat) {
        this.decstat = decstat;
    }

    public Integer getDenumlg() {
        return denumlg;
    }

    public void setDenumlg(Integer denumlg) {
        this.denumlg = denumlg;
    }

    public Integer getDekmsta() {
        return dekmsta;
    }

    public void setDekmsta(Integer dekmsta) {
        this.dekmsta = dekmsta;
    }

    public Integer getDedurtr() {
        return dedurtr;
    }

    public void setDedurtr(Integer dedurtr) {
        this.dedurtr = dedurtr;
    }

    public Integer getDeescale() {
        return deescale;
    }

    public void setDeescale(Integer deescale) {
        this.deescale = deescale;
    }

    public String getEmbra() {
        return embra;
    }

    public void setEmbra(String embra) {
        this.embra = embra;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getDectyst() {
        return dectyst;
    }

    public void setDectyst(String dectyst) {
        this.dectyst = dectyst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItineraireDTO)) {
            return false;
        }

        ItineraireDTO itineraireDTO = (ItineraireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itineraireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItineraireDTO{" +
            "id='" + getId() + "'" +
            ", deccent=" + getDeccent() +
            ", decagenc=" + getDecagenc() +
            ", denumli='" + getDenumli() + "'" +
            ", decstat='" + getDecstat() + "'" +
            ", denumlg=" + getDenumlg() +
            ", dekmsta=" + getDekmsta() +
            ", dedurtr=" + getDedurtr() +
            ", deescale=" + getDeescale() +
            ", embra='" + getEmbra() + "'" +
            ", section=" + getSection() +
            ", sens='" + getSens() + "'" +
            ", dectyst='" + getDectyst() + "'" +
            "}";
    }
}
