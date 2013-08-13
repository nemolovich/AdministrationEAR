/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brian GOHIER
 */
@Entity
@Table(name = "WORKSTATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workstation.findAll", query = "SELECT w FROM Workstation w"),
    @NamedQuery(name = "Workstation.findById", query = "SELECT w FROM Workstation w WHERE w.id = :id"),
    @NamedQuery(name = "Workstation.findByWsType", query = "SELECT w FROM Workstation w WHERE w.wsType = :wsType"),
    @NamedQuery(name = "Workstation.findByName", query = "SELECT w FROM Workstation w WHERE w.name = :name"),
    @NamedQuery(name = "Workstation.findByUserNameDefault", query = "SELECT w FROM Workstation w WHERE w.userNameDefault = :userNameDefault"),
    @NamedQuery(name = "Workstation.findByIpAddress", query = "SELECT w FROM Workstation w WHERE w.ipAddress = :ipAddress"),
    @NamedQuery(name = "Workstation.findByBrand", query = "SELECT w FROM Workstation w WHERE w.brand = :brand"),
    @NamedQuery(name = "Workstation.findByStartDate", query = "SELECT w FROM Workstation w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "Workstation.findByProcessor", query = "SELECT w FROM Workstation w WHERE w.processor = :processor"),
    @NamedQuery(name = "Workstation.findByMonitor", query = "SELECT w FROM Workstation w WHERE w.monitor = :monitor"),
    @NamedQuery(name = "Workstation.findByVideoCard", query = "SELECT w FROM Workstation w WHERE w.videoCard = :videoCard"),
    @NamedQuery(name = "Workstation.findByOperatingSystem", query = "SELECT w FROM Workstation w WHERE w.operatingSystem = :operatingSystem"),
    @NamedQuery(name = "Workstation.findBySystemVersion", query = "SELECT w FROM Workstation w WHERE w.systemVersion = :systemVersion"),
    @NamedQuery(name = "Workstation.findBySystemLicense", query = "SELECT w FROM Workstation w WHERE w.systemLicense = :systemLicense"),
    @NamedQuery(name = "Workstation.findByRam", query = "SELECT w FROM Workstation w WHERE w.ram = :ram"),
    @NamedQuery(name = "Workstation.findByHardDrive", query = "SELECT w FROM Workstation w WHERE w.hardDrive = :hardDrive"),
    @NamedQuery(name = "Workstation.findByObservations", query = "SELECT w FROM Workstation w WHERE w.observations = :observations"),
    @NamedQuery(name = "Workstation.findBySleeping", query = "SELECT w FROM Workstation w WHERE w.sleeping = :sleeping")})
public class Workstation implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 64)
    @Column(name = "WS_TYPE")
    private String wsType;
    @Basic(optional = false)
    @NotNull
    @Size(max = 64)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "USER_NAME_DEFAULT")
    private String userNameDefault;
    @Size(max = 39)
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
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
    @Column(name = "SYSTEM_VERSION")
    private String systemVersion;
    @Size(max = 64)
    @Column(name = "SYSTEM_LICENSE")
    private String systemLicense;
    @Size(max = 64)
    @Column(name = "RAM")
    private String ram;
    @Size(max = 64)
    @Column(name = "HARD_DRIVE")
    private String hardDrive;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "ID_FILE_PATH", referencedColumnName = "ID")
    @ManyToOne
    private FilePath idFilePath;
    @OneToMany(mappedBy = "idWorkstation")
    private List<Task> taskList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNameDefault() {
        return userNameDefault;
    }

    public void setUserNameDefault(String userNameDefault) {
        this.userNameDefault = userNameDefault;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemLicense() {
        return systemLicense;
    }

    public void setSystemLicense(String systemLicense) {
        this.systemLicense = systemLicense;
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

    public Boolean getSleeping() {
        return sleeping;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public FilePath getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(FilePath idFilePath) {
        this.idFilePath = idFilePath;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
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

    public String getFullString()
    {
        return "entity.Workstation{" + "id=" + id + ", wsType=" + wsType + ", name=" + name + ", userNameDefault=" + userNameDefault + ", ipAddress=" + ipAddress + ", brand=" + brand + ", startDate=" + startDate + ", processor=" + processor + ", monitor=" + monitor + ", videoCard=" + videoCard + ", operatingSystem=" + operatingSystem + ", systemVersion=" + systemVersion + ", systemLicense=" + systemLicense + ", ram=" + ram + ", hardDrive=" + hardDrive + ", observations=" + observations + ", sleeping=" + sleeping + ", idClient=" + idClient + ", idFilePath=" + idFilePath + ", taskList=" + taskList + '}';
    }
    
    @Override
    public String toString() {
        String out=this.name+" ["+this.brand+"] on "+this.operatingSystem+" [P:"+
                (this.processor!=null&&!this.processor.isEmpty()?this.processor:"null")+
                "|RAM:"+(this.ram!=null&&!this.ram.isEmpty()?this.ram:"null")+"][";
        if(this.startDate!=null)
        {
            out+=DateFormat.getDateInstance().format(this.startDate)+"][";
        }
        out+=this.idClient+"]";
        return out;
    }
}
