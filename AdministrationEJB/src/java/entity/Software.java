/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "SOFTWARE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Software.findAll", query = "SELECT s FROM Software s"),
    @NamedQuery(name = "Software.findById", query = "SELECT s FROM Software s WHERE s.id = :id"),
    @NamedQuery(name = "Software.findByName", query = "SELECT s FROM Software s WHERE s.name = :name"),
    @NamedQuery(name = "Software.findByVersion", query = "SELECT s FROM Software s WHERE s.version = :version"),
    @NamedQuery(name = "Software.findByLicense", query = "SELECT s FROM Software s WHERE s.license = :license"),
    @NamedQuery(name = "Software.findByEditor", query = "SELECT s FROM Software s WHERE s.editor = :editor"),
    @NamedQuery(name = "Software.findByStationNumber", query = "SELECT s FROM Software s WHERE s.stationNumber = :stationNumber"),
    @NamedQuery(name = "Software.findByObservations", query = "SELECT s FROM Software s WHERE s.observations = :observations")})
public class Software implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 64)
    @Column(name = "NAME")
    private String name;
    @Size(max = 64)
    @Column(name = "VERSION")
    private String version;
    @Size(max = 64)
    @Column(name = "LICENSE")
    private String license;
    @Size(max = 64)
    @Column(name = "EDITOR")
    private String editor;
    @Column(name = "STATION_NUMBER")
    private Integer stationNumber;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;

    public Software() {
    }

    public Software(Integer id) {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id==null?-1:this.id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
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
        if (!(object instanceof Software)) {
            return false;
        }
        Software other = (Software) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name+" [v"+this.version+" by "+this.editor+"]";
    }
    
}
