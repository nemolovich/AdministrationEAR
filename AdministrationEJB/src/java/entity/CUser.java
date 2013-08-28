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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "C_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CUser.findAll", query = "SELECT c FROM CUser c"),
    @NamedQuery(name = "CUser.findById", query = "SELECT c FROM CUser c WHERE c.id = :id"),
    @NamedQuery(name = "CUser.findByName", query = "SELECT c FROM CUser c WHERE c.name = :name"),
    @NamedQuery(name = "CUser.findByDirectPhone", query = "SELECT c FROM CUser c WHERE c.directPhone = :directPhone"),
    @NamedQuery(name = "CUser.findByPosition", query = "SELECT c FROM CUser c WHERE c.position = :position"),
    @NamedQuery(name = "CUser.findByLogin", query = "SELECT c FROM CUser c WHERE c.login = :login"),
    @NamedQuery(name = "CUser.findByPassword", query = "SELECT c FROM CUser c WHERE c.password = :password"),
    @NamedQuery(name = "CUser.findByMails", query = "SELECT c FROM CUser c WHERE c.mails = :mails"),
    @NamedQuery(name = "CUser.findByPhone", query = "SELECT c FROM CUser c WHERE c.phone = :phone"),
    @NamedQuery(name = "CUser.findByObservations", query = "SELECT c FROM CUser c WHERE c.observations = :observations"),
    @NamedQuery(name = "CUser.findBySleeping", query = "SELECT c FROM CUser c WHERE c.sleeping = :sleeping")})
public class CUser implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(name = "NAME")
    private String name;
    @Size(max = 14)
    @Column(name = "DIRECT_PHONE")
    private String directPhone;
    @Size(max = 30)
    @Column(name = "POSITION")
    private String position;
    @Size(max = 30)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 64)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 250)
    @Column(name = "MAILS")
    private String mails;
    @Size(max = 14)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @OneToMany(mappedBy = "idUser")
    private List<Client> clientList;
    @OneToMany(mappedBy = "idUser")
    private List<Task> taskList;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "ID_FILE_PATH", referencedColumnName = "ID")
    @OneToOne(optional = true)
    private FilePath idFilePath;

    public CUser() {
    }

    public CUser(Integer id) {
        this.id = id;
    }

    public CUser(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String getDirectPhone() {
        return directPhone;
    }

    public void setDirectPhone(String directPhone) {
        this.directPhone = directPhone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @XmlTransient
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
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
        if (!(object instanceof CUser)) {
            return false;
        }
        CUser other = (CUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.CUser{" + "id=" + id + ", name=" + name + ", directPhone=" + directPhone + ", position=" + position + ", login=" + login + ", password=" + password + ", mails=" + mails + ", phone=" + phone + ", observations=" + observations + ", sleeping=" + sleeping + ", clientList=" + clientList + ", taskList=" + taskList + ", idClient=" + idClient + ", idFilePath=" + idFilePath + '}';
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
