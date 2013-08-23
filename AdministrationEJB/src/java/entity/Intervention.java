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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "INTERVENTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intervention.findAll", query = "SELECT i FROM Intervention i"),
    @NamedQuery(name = "Intervention.findById", query = "SELECT i FROM Intervention i WHERE i.id = :id"),
    @NamedQuery(name = "Intervention.findByInterventionDate", query = "SELECT i FROM Intervention i WHERE i.interventionDate = :interventionDate"),
    @NamedQuery(name = "Intervention.findByDuration", query = "SELECT i FROM Intervention i WHERE i.duration = :duration"),
    @NamedQuery(name = "Intervention.findByDeplacement", query = "SELECT i FROM Intervention i WHERE i.deplacement = :deplacement"),
    @NamedQuery(name = "Intervention.findBySleeping", query = "SELECT i FROM Intervention i WHERE i.sleeping = :sleeping")})
public class Intervention implements Serializable, Comparable<Intervention>
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "INTERVENTION_DATE")
    @Temporal(TemporalType.DATE)
    private Date interventionDate;
    @Column(name = "DURATION")
    private Double duration;
    @Column(name = "DEPLACEMENT")
    private Boolean deplacement=false;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_TASK", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Task idTask;
    @JoinColumn(name = "ID_FACTURE", referencedColumnName = "ID")
    @ManyToOne
    private Facture idFacture;

    public Intervention() {
    }

    public Intervention(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInterventionDate() {
        return interventionDate;
    }

    public void setInterventionDate(Date interventionDate) {
        this.interventionDate = interventionDate;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Boolean getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Boolean deplacement) {
        this.deplacement = deplacement;
    }

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public Task getIdTask() {
        return idTask;
    }

    public void setIdTask(Task idTask) {
        this.idTask = idTask;
    }

    public Facture getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Facture idFacture) {
        this.idFacture = idFacture;
    }

    @Override
    public int compareTo(Intervention i)
    {
        return -i.getInterventionDate().compareTo(this.interventionDate);
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
        if (!(object instanceof Intervention)) {
            return false;
        }
        Intervention other = (Intervention) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.Intervention{" + "id=" + id + ", interventionDate=" + interventionDate + ", duration=" + duration + ", deplacement=" + deplacement + ", sleeping=" + sleeping + ", idTask=" + idTask + ", idFacture=" + idFacture + '}';
    }

    @Override
    public String toString() {
        return "Intervention sur la tâche {"+this.idTask+"}";
    }
}
