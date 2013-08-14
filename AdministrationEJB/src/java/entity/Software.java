/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Software.findBySerialNumber", query = "SELECT s FROM Software s WHERE s.serialNumber = :serialNumber"),
    @NamedQuery(name = "Software.findByStartDate", query = "SELECT s FROM Software s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "Software.findByEditor", query = "SELECT s FROM Software s WHERE s.editor = :editor"),
    @NamedQuery(name = "Software.findByObservations", query = "SELECT s FROM Software s WHERE s.observations = :observations"),
    @NamedQuery(name = "Software.findBySleeping", query = "SELECT s FROM Software s WHERE s.sleeping = :sleeping")})
public class Software implements Serializable
{
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
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Size(max = 64)
    @Column(name = "EDITOR")
    private String editor;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "ID_FILE_PATH", referencedColumnName = "ID")
    @ManyToOne
    private FilePath idFilePath;

    public Software() {
    }

    public Software(Integer id) {
        this.id = id;
    }

    public Integer getId() {
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public FilePath getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(FilePath idFilePath) {
        this.idFilePath = idFilePath;
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

    public String getFullString()
    {
        return "entity.Software{" + "id=" + id + ", name=" + name + ", version=" + version + ", license=" + license + ", serialNumber=" + serialNumber + ", startDate=" + startDate + ", editor=" + editor + ", observations=" + observations + ", sleeping=" + sleeping + ", idClient=" + idClient + ", idFilePath=" + idFilePath + '}';
    }
    
    @Override
    public String toString() {
        return this.name+" ["+this.version+" by "+this.editor+"]";
    }
    
}
