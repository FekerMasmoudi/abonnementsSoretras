package tn.soretras.clientsmanager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tn.soretras.clientsmanager.domain.enumeration.ETypeClient;

/**
 * A Client.
 */
@Document(collection = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("nompre")
    private String nompre;

    @NotNull(message = "must not be null")
    @Field("typeclt")
    private ETypeClient typeclt;

    @NotNull(message = "must not be null")
    @Field("datenais")
    private LocalDate datenais;

    @NotNull(message = "must not be null")
    @Field("cin")
    private String cin;

    @Field("dateedit")
    private LocalDate dateedit;

    @Field("identuniq")
    private String identuniq;

    @Field("idetablissement")
    private String idetablissement;

    @Field("classe")
    private String classe;

    @Field("nomprepar")
    private String nomprepar;

    @Field("addr")
    private String addr;

    @Field("email")
    private String email;

    @NotNull(message = "must not be null")
    @Field("phone")
    private String phone;

    @NotNull(message = "must not be null")
    @Field("img")
    private String img;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNompre() {
        return this.nompre;
    }

    public Client nompre(String nompre) {
        this.setNompre(nompre);
        return this;
    }

    public void setNompre(String nompre) {
        this.nompre = nompre;
    }

    public ETypeClient getTypeclt() {
        return this.typeclt;
    }

    public Client typeclt(ETypeClient typeclt) {
        this.setTypeclt(typeclt);
        return this;
    }

    public void setTypeclt(ETypeClient typeclt) {
        this.typeclt = typeclt;
    }

    public LocalDate getDatenais() {
        return this.datenais;
    }

    public Client datenais(LocalDate datenais) {
        this.setDatenais(datenais);
        return this;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public String getCin() {
        return this.cin;
    }

    public Client cin(String cin) {
        this.setCin(cin);
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDateedit() {
        return this.dateedit;
    }

    public Client dateedit(LocalDate dateedit) {
        this.setDateedit(dateedit);
        return this;
    }

    public void setDateedit(LocalDate dateedit) {
        this.dateedit = dateedit;
    }

    public String getIdentuniq() {
        return this.identuniq;
    }

    public Client identuniq(String identuniq) {
        this.setIdentuniq(identuniq);
        return this;
    }

    public void setIdentuniq(String identuniq) {
        this.identuniq = identuniq;
    }

    public String getIdetablissement() {
        return this.idetablissement;
    }

    public Client idetablissement(String idetablissement) {
        this.setIdetablissement(idetablissement);
        return this;
    }

    public void setIdetablissement(String idetablissement) {
        this.idetablissement = idetablissement;
    }

    public String getClasse() {
        return this.classe;
    }

    public Client classe(String classe) {
        this.setClasse(classe);
        return this;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNomprepar() {
        return this.nomprepar;
    }

    public Client nomprepar(String nomprepar) {
        this.setNomprepar(nomprepar);
        return this;
    }

    public void setNomprepar(String nomprepar) {
        this.nomprepar = nomprepar;
    }

    public String getAddr() {
        return this.addr;
    }

    public Client addr(String addr) {
        this.setAddr(addr);
        return this;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Client phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return this.img;
    }

    public Client img(String img) {
        this.setImg(img);
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nompre='" + getNompre() + "'" +
            ", typeclt='" + getTypeclt() + "'" +
            ", datenais='" + getDatenais() + "'" +
            ", cin='" + getCin() + "'" +
            ", dateedit='" + getDateedit() + "'" +
            ", identuniq='" + getIdentuniq() + "'" +
            ", idetablissement='" + getIdetablissement() + "'" +
            ", classe='" + getClasse() + "'" +
            ", nomprepar='" + getNomprepar() + "'" +
            ", addr='" + getAddr() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", img='" + getImg() + "'" +
            "}";
    }
}
