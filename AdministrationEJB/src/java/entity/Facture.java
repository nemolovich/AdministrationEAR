/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "FACTURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facture.findAll", query = "SELECT f FROM Facture f"),
    @NamedQuery(name = "Facture.findById", query = "SELECT f FROM Facture f WHERE f.id = :id"),
    @NamedQuery(name = "Facture.findByFactureNumber", query = "SELECT f FROM Facture f WHERE f.factureNumber = :factureNumber"),
    @NamedQuery(name = "Facture.findBySleeping", query = "SELECT f FROM Facture f WHERE f.sleeping = :sleeping")})
public class Facture implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 8)
    @Column(name = "FACTURE_NUMBER")
    private String factureNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @OneToMany(mappedBy = "idFacture")
    private List<Intervention> interventionList;

    public Facture() {
    }

    public Facture(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactureNumber() {
        return factureNumber;
    }

    public void setFactureNumber(String factureNumber) {
        this.factureNumber = factureNumber;
    }

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    @XmlTransient
    public List<Intervention> getInterventionList() {
        return interventionList;
    }

    public void setInterventionList(List<Intervention> interventionList) {
        this.interventionList = interventionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Facture other = (Facture) obj;
        if ((this.factureNumber == null) ? (other.factureNumber != null) : !this.factureNumber.equals(other.factureNumber)) {
            return false;
        }
        if (this.sleeping != other.sleeping && (this.sleeping == null || !this.sleeping.equals(other.sleeping))) {
            return false;
        }
        return true;
    }
    
    public String getFullString()
    {
        return "entity.Facture{" + "id=" + id + ", factureNumber=" + factureNumber + ", sleeping=" + sleeping + ", interventionList=" + interventionList + '}';
    }

    @Override
    public String toString() {
        return this.factureNumber;
    }
}
