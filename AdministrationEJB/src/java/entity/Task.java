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
    @NamedQuery(name = "Task.findByIntendedDuration", query = "SELECT t FROM Task t WHERE t.intendedDuration = :intendedDuration"),
    @NamedQuery(name = "Task.findByDeplacement", query = "SELECT t FROM Task t WHERE t.deplacement = :deplacement"),
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
    @Size(max = 30)
    @Column(name = "INTENDED_DURATION")
    private String intendedDuration;
    @Column(name = "DEPLACEMENT")
    private Boolean deplacement=false;
    @Size(max = 10)
    @Column(name = "INTERVENTION_TYPE")
    private String interventionType;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_WORKSTATION", referencedColumnName = "ID")
    @ManyToOne
    private Workstation idWorkstation;
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

    public String getIntendedDuration() {
        return intendedDuration;
    }

    public void setIntendedDuration(String intendedDuration) {
        this.intendedDuration = intendedDuration;
    }

    public Boolean getDeplacement() {
        return deplacement==null?false:deplacement;
    }

    public void setDeplacement(Boolean deplacement) {
        this.deplacement = deplacement;
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

    public Workstation getIdWorkstation() {
        return idWorkstation;
    }

    public void setIdWorkstation(Workstation idWorkstation) {
        this.idWorkstation = idWorkstation;
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
    public List<Intervention> getInterventionList()
    {
        return interventionList;
    }

    public void setInterventionList(List<Intervention> interventionList)
    {
        this.interventionList = interventionList;
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
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.Task{" + "id=" + id + ", description=" + description + ", intendedDuration=" + intendedDuration + ", deplacement=" + deplacement + ", interventionType=" + interventionType + ", observations=" + observations + ", sleeping=" + sleeping + ", idWorkstation=" + idWorkstation + ", idUser=" + idUser + ", idClient=" + idClient + '}';
    }

    @Override
    public String toString()
    {
        return this.description+" pour la société "+this.idClient;
    }

}
