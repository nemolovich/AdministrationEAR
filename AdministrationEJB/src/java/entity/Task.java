/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "TASK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findById", query = "SELECT t FROM Task t WHERE t.id = :id"),
    @NamedQuery(name = "Task.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description"),
    @NamedQuery(name = "Task.findByStartDate", query = "SELECT t FROM Task t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "Task.findByIntendedDuration", query = "SELECT t FROM Task t WHERE t.intendedDuration = :intendedDuration"),
    @NamedQuery(name = "Task.findByInterventionType", query = "SELECT t FROM Task t WHERE t.interventionType = :interventionType"),
    @NamedQuery(name = "Task.findByObservations", query = "SELECT t FROM Task t WHERE t.observations = :observations"),
    @NamedQuery(name = "Task.findBySleeping", query = "SELECT t FROM Task t WHERE t.sleeping = :sleeping")})
    public class Task implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate=Calendar.getInstance(Locale.FRANCE).getTime();
    @Column(name = "INTENDED_DURATION")
    private Double intendedDuration;
    @Size(max = 10)
    @Column(name = "INTERVENTION_TYPE")
    private String interventionType;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_DEVICE", referencedColumnName = "ID")
    @ManyToOne
    private Device idDevice;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private CUser idUser;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTask")
    private List<Intervention> interventionList;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getIntendedDuration() {
        return intendedDuration;
    }

    public void setIntendedDuration(Double intendedDuration) {
        this.intendedDuration = intendedDuration;
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

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(String sleeping) {
        this.sleeping = Boolean.valueOf(sleeping);
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public Device getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Device idDevice) {
        this.idDevice = idDevice;
    }

    public CUser getIdUser() {
        return idUser;
    }

    public void setIdUser(CUser idUser) {
        this.idUser = idUser;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
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
        int hash = 3;
        hash = 67 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 67 * hash + (this.startDate != null ? this.startDate.hashCode() : 0);
        hash = 67 * hash + (this.intendedDuration != null ? this.intendedDuration.hashCode() : 0);
        hash = 67 * hash + (this.interventionType != null ? this.interventionType.hashCode() : 0);
        hash = 67 * hash + (this.observations != null ? this.observations.hashCode() : 0);
        hash = 67 * hash + (this.sleeping != null ? this.sleeping.hashCode() : 0);
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
        final Task other = (Task) obj;
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.startDate != other.startDate && (this.startDate == null || !this.startDate.equals(other.startDate))) {
            return false;
        }
        if (this.intendedDuration != other.intendedDuration && (this.intendedDuration == null || !this.intendedDuration.equals(other.intendedDuration))) {
            return false;
        }
        if ((this.interventionType == null) ? (other.interventionType != null) : !this.interventionType.equals(other.interventionType)) {
            return false;
        }
        if ((this.observations == null) ? (other.observations != null) : !this.observations.equals(other.observations)) {
            return false;
        }
        if (this.sleeping != other.sleeping && (this.sleeping == null || !this.sleeping.equals(other.sleeping))) {
            return false;
        }
        return true;
    }
    
    public String getFullString()
    {
        return "entity.Task{" + "id=" + id + ", description=" + description + ", startDate=" + startDate + ", intendedDuration=" + intendedDuration + ", interventionType=" + interventionType + ", observations=" + observations + ", sleeping=" + sleeping + ", idDevice=" + idDevice + ", idUser=" + idUser + ", idClient=" + idClient + ", interventionList=" + interventionList + '}';
    }
    
    @Override
    public String toString()
    {
        return this.description+" pour la société "+this.idClient;
    }
    
}
