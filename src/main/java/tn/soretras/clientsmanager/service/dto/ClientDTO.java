package tn.soretras.clientsmanager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import tn.soretras.clientsmanager.domain.enumeration.ETypeClient;

/**
 * A DTO for the {@link tn.soretras.clientsmanager.domain.Client} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String nompre;

    @NotNull(message = "must not be null")
    private ETypeClient typeclt;

    @NotNull(message = "must not be null")
    private LocalDate datenais;

    @NotNull(message = "must not be null")
    private String cin;

    private LocalDate dateedit;

    private String identuniq;

    private String idetablissement;

    private String classe;

    private String nomprepar;

    private String addr;

    private String email;

    @NotNull(message = "must not be null")
    private String phone;

    @NotNull(message = "must not be null")
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNompre() {
        return nompre;
    }

    public void setNompre(String nompre) {
        this.nompre = nompre;
    }

    public ETypeClient getTypeclt() {
        return typeclt;
    }

    public void setTypeclt(ETypeClient typeclt) {
        this.typeclt = typeclt;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDateedit() {
        return dateedit;
    }

    public void setDateedit(LocalDate dateedit) {
        this.dateedit = dateedit;
    }

    public String getIdentuniq() {
        return identuniq;
    }

    public void setIdentuniq(String identuniq) {
        this.identuniq = identuniq;
    }

    public String getIdetablissement() {
        return idetablissement;
    }

    public void setIdetablissement(String idetablissement) {
        this.idetablissement = idetablissement;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNomprepar() {
        return nomprepar;
    }

    public void setNomprepar(String nomprepar) {
        this.nomprepar = nomprepar;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id='" + getId() + "'" +
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
