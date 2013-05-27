/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Stage
 */
@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByAddress", query = "SELECT c FROM Client c WHERE c.address = :address"),
    @NamedQuery(name = "Client.findByPhone", query = "SELECT c FROM Client c WHERE c.phone = :phone"),
    @NamedQuery(name = "Client.findByFax", query = "SELECT c FROM Client c WHERE c.fax = :fax"),
    @NamedQuery(name = "Client.findByMail", query = "SELECT c FROM Client c WHERE c.mail = :mail"),
    @NamedQuery(name = "Client.findByInterventionType", query = "SELECT c FROM Client c WHERE c.interventionType = :interventionType"),
    @NamedQuery(name = "Client.findByObservations", query = "SELECT c FROM Client c WHERE c.observations = :observations")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "ADDRESS")
    private String address;
    // @Pattern(regexp="^\\(?(\\d{2})\\)?[. ]?(\\d{2})?[. ]?(\\d{2})?[. ]?(\\d{2})[. ]?(\\d{2})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 14, max = 14)
    @Column(name = "PHONE")
    private String phone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 14)
    @Column(name = "FAX")
    private String fax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAIL")
    private String mail;
    @Size(max = 250)
    @Column(name = "INTERVENTION_TYPE")
    private String interventionType;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private CUser idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private List<CUser> cUserList;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, String name, String address, String phone, String mail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getInterventionType() {
        return interventionType;
    }

    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public CUser getIdUser() {
        return idUser;
    }

    public void setIdUser(CUser idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<CUser> getCUserList() {
        return cUserList;
    }

    public void setCUserList(List<CUser> cUserList) {
        this.cUserList = cUserList;
    }
    
    public String getUserName()
    {
        return this.idUser.getName();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
