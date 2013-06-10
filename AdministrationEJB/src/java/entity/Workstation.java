/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Windows 7
 */
@Entity
@Table(name = "WORKSTATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workstation.findAll", query = "SELECT w FROM Workstation w"),
    @NamedQuery(name = "Workstation.findById", query = "SELECT w FROM Workstation w WHERE w.id = :id"),
    @NamedQuery(name = "Workstation.findByWsType", query = "SELECT w FROM Workstation w WHERE w.wsType = :wsType"),
    @NamedQuery(name = "Workstation.findByBrand", query = "SELECT w FROM Workstation w WHERE w.brand = :brand"),
    @NamedQuery(name = "Workstation.findByStartDate", query = "SELECT w FROM Workstation w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "Workstation.findByProcessor", query = "SELECT w FROM Workstation w WHERE w.processor = :processor"),
    @NamedQuery(name = "Workstation.findByMonitor", query = "SELECT w FROM Workstation w WHERE w.monitor = :monitor"),
    @NamedQuery(name = "Workstation.findByVideoCard", query = "SELECT w FROM Workstation w WHERE w.videoCard = :videoCard"),
    @NamedQuery(name = "Workstation.findByOperatingSystem", query = "SELECT w FROM Workstation w WHERE w.operatingSystem = :operatingSystem"),
    @NamedQuery(name = "Workstation.findByRam", query = "SELECT w FROM Workstation w WHERE w.ram = :ram"),
    @NamedQuery(name = "Workstation.findByHardDrive", query = "SELECT w FROM Workstation w WHERE w.hardDrive = :hardDrive"),
    @NamedQuery(name = "Workstation.findByObservations", query = "SELECT w FROM Workstation w WHERE w.observations = :observations")})
public class Workstation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 64)
    @Column(name = "WS_TYPE")
    private String wsType;
    @Size(max = 64)
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate=null;
    @Size(max = 64)
    @Column(name = "PROCESSOR")
    private String processor;
    @Size(max = 64)
    @Column(name = "MONITOR")
    private String monitor;
    @Size(max = 64)
    @Column(name = "VIDEO_CARD")
    private String videoCard;
    @Size(max = 64)
    @Column(name = "OPERATING_SYSTEM")
    private String operatingSystem;
    @Size(max = 64)
    @Column(name = "RAM")
    private String ram;
    @Size(max = 64)
    @Column(name = "HARD_DRIVE")
    private String hardDrive;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;

    public Workstation() {
    }

    public Workstation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWsType() {
        return wsType;
    }

    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
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
        if (!(object instanceof Workstation)) {
            return false;
        }
        Workstation other = (Workstation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        String out=this.brand+" on "+this.operatingSystem+" [P:"+
                this.processor+",RAM:"+this.ram+"][";
        if(this.startDate!=null)
        {
            out+=DateFormat.getDateInstance().format(this.startDate)+"][";
        }
        out+=this.idClient+"]";
        return out;
    }
    
}
