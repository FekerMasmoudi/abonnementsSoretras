package tn.soretras.agencesmananger.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Agence.
 */
@Document(collection = "agence")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("deccent")
    private Integer deccent;

    @NotNull(message = "must not be null")
    @Field("decagenc")
    private Integer decagenc;

    @Field("delagenc")
    private String delagenc;

    @Field("delagencfr")
    private String delagencfr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Agence id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeccent() {
        return this.deccent;
    }

    public Agence deccent(Integer deccent) {
        this.setDeccent(deccent);
        return this;
    }

    public void setDeccent(Integer deccent) {
        this.deccent = deccent;
    }

    public Integer getDecagenc() {
        return this.decagenc;
    }

    public Agence decagenc(Integer decagenc) {
        this.setDecagenc(decagenc);
        return this;
    }

    public void setDecagenc(Integer decagenc) {
        this.decagenc = decagenc;
    }

    public String getDelagenc() {
        return this.delagenc;
    }

    public Agence delagenc(String delagenc) {
        this.setDelagenc(delagenc);
        return this;
    }

    public void setDelagenc(String delagenc) {
        this.delagenc = delagenc;
    }

    public String getDelagencfr() {
        return this.delagencfr;
    }

    public Agence delagencfr(String delagencfr) {
        this.setDelagencfr(delagencfr);
        return this;
    }

    public void setDelagencfr(String delagencfr) {
        this.delagencfr = delagencfr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return id != null && id.equals(((Agence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", deccent=" + getDeccent() +
            ", decagenc=" + getDecagenc() +
            ", delagenc='" + getDelagenc() + "'" +
            ", delagencfr='" + getDelagencfr() + "'" +
            "}";
    }
}
