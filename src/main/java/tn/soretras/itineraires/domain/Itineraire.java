package tn.soretras.itineraires.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Itineraire.
 */
@Document(collection = "itineraire")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Itineraire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("deccent")
    private Integer deccent;

    @NotNull(message = "must not be null")
    @Field("decagenc")
    private Integer decagenc;

    @NotNull(message = "must not be null")
    @Field("denumli")
    private String denumli;

    @NotNull(message = "must not be null")
    @Field("decstat")
    private String decstat;

    @NotNull(message = "must not be null")
    @Field("denumlg")
    private Integer denumlg;

    @Field("dekmsta")
    private Integer dekmsta;

    @Field("dedurtr")
    private Integer dedurtr;

    @Field("deescale")
    private Integer deescale;

    @Field("embra")
    private String embra;

    @Field("section")
    private Integer section;

    @Field("sens")
    private String sens;

    @Field("dectyst")
    private String dectyst;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Itineraire id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeccent() {
        return this.deccent;
    }

    public Itineraire deccent(Integer deccent) {
        this.setDeccent(deccent);
        return this;
    }

    public void setDeccent(Integer deccent) {
        this.deccent = deccent;
    }

    public Integer getDecagenc() {
        return this.decagenc;
    }

    public Itineraire decagenc(Integer decagenc) {
        this.setDecagenc(decagenc);
        return this;
    }

    public void setDecagenc(Integer decagenc) {
        this.decagenc = decagenc;
    }

    public String getDenumli() {
        return this.denumli;
    }

    public Itineraire denumli(String denumli) {
        this.setDenumli(denumli);
        return this;
    }

    public void setDenumli(String denumli) {
        this.denumli = denumli;
    }

    public String getDecstat() {
        return this.decstat;
    }

    public Itineraire decstat(String decstat) {
        this.setDecstat(decstat);
        return this;
    }

    public void setDecstat(String decstat) {
        this.decstat = decstat;
    }

    public Integer getDenumlg() {
        return this.denumlg;
    }

    public Itineraire denumlg(Integer denumlg) {
        this.setDenumlg(denumlg);
        return this;
    }

    public void setDenumlg(Integer denumlg) {
        this.denumlg = denumlg;
    }

    public Integer getDekmsta() {
        return this.dekmsta;
    }

    public Itineraire dekmsta(Integer dekmsta) {
        this.setDekmsta(dekmsta);
        return this;
    }

    public void setDekmsta(Integer dekmsta) {
        this.dekmsta = dekmsta;
    }

    public Integer getDedurtr() {
        return this.dedurtr;
    }

    public Itineraire dedurtr(Integer dedurtr) {
        this.setDedurtr(dedurtr);
        return this;
    }

    public void setDedurtr(Integer dedurtr) {
        this.dedurtr = dedurtr;
    }

    public Integer getDeescale() {
        return this.deescale;
    }

    public Itineraire deescale(Integer deescale) {
        this.setDeescale(deescale);
        return this;
    }

    public void setDeescale(Integer deescale) {
        this.deescale = deescale;
    }

    public String getEmbra() {
        return this.embra;
    }

    public Itineraire embra(String embra) {
        this.setEmbra(embra);
        return this;
    }

    public void setEmbra(String embra) {
        this.embra = embra;
    }

    public Integer getSection() {
        return this.section;
    }

    public Itineraire section(Integer section) {
        this.setSection(section);
        return this;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getSens() {
        return this.sens;
    }

    public Itineraire sens(String sens) {
        this.setSens(sens);
        return this;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getDectyst() {
        return this.dectyst;
    }

    public Itineraire dectyst(String dectyst) {
        this.setDectyst(dectyst);
        return this;
    }

    public void setDectyst(String dectyst) {
        this.dectyst = dectyst;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Itineraire)) {
            return false;
        }
        return id != null && id.equals(((Itineraire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Itineraire{" +
            "id=" + getId() +
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
