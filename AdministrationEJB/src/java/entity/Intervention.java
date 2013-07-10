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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Intervention.findByDuration", query = "SELECT i FROM Intervention i WHERE i.duration = :duration"),
    @NamedQuery(name = "Intervention.findByFactureNumber", query = "SELECT i FROM Intervention i WHERE i.factureNumber = :factureNumber"),
    @NamedQuery(name = "Intervention.findBySleeping", query = "SELECT i FROM Intervention i WHERE i.sleeping = :sleeping")})
public class Intervention implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "DURATION")
    private String duration;
    @Column(name = "FACTURE_NUMBER")
    private Integer factureNumber;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_TASK", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Task idTask;

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getFactureNumber() {
        return factureNumber;
    }

    public void setFactureNumber(Integer factureNumber) {
        this.factureNumber = factureNumber;
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
        return "Intervention sur la tâche {"+this.idTask+"}";
    }

    @Override
    public String toString() {
        return "entity.Intervention{" + "id=" + id + ", duration=" + duration + ", factureNumber=" + factureNumber + ", sleeping=" + sleeping + ", idTask=" + idTask + '}';
    }
    
}
