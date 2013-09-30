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
@Table(name = "MAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mail.findAll", query = "SELECT m FROM Mail m"),
    @NamedQuery(name = "Mail.findById", query = "SELECT m FROM Mail m WHERE m.id = :id"),
    @NamedQuery(name = "Mail.findByMail", query = "SELECT m FROM Mail m WHERE m.mail = :mail"),
    @NamedQuery(name = "Mail.findByPop", query = "SELECT m FROM Mail m WHERE m.pop = :pop"),
    @NamedQuery(name = "Mail.findByPopPassword", query = "SELECT m FROM Mail m WHERE m.popPassword = :popPassword"),
    @NamedQuery(name = "Mail.findBySmtp", query = "SELECT m FROM Mail m WHERE m.smtp = :smtp"),
    @NamedQuery(name = "Mail.findBySmtpPassword", query = "SELECT m FROM Mail m WHERE m.smtpPassword = :smtpPassword"),
    @NamedQuery(name = "Mail.findByObservations", query = "SELECT m FROM Mail m WHERE m.observations = :observations"),
    @NamedQuery(name = "Mail.findBySleeping", query = "SELECT m FROM Mail m WHERE m.sleeping = :sleeping")})
public class Mail implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 64)
    @Column(name = "MAIL")
    private String mail;
    @Size(max = 64)
    @Column(name = "POP")
    private String pop;
    @Size(max = 64)
    @Column(name = "POP_PASSWORD")
    private String popPassword;
    @Size(max = 64)
    @Column(name = "SMTP")
    private String smtp;
    @Size(max = 64)
    @Column(name = "SMTP_PASSWORD")
    private String smtpPassword;
    @Size(max = 250)
    @Column(name = "OBSERVATIONS")
    private String observations;
    @Column(name = "SLEEPING")
    private Boolean sleeping=false;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client idClient;

    public Mail() {
    }

    public Mail(Integer id) {
        this.id = id;
    }

    public Mail(Integer id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPopPassword() {
        return popPassword;
    }

    public void setPopPassword(String popPassword) {
        this.popPassword = popPassword;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final Mail other = (Mail) obj;
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        if ((this.pop == null) ? (other.pop != null) : !this.pop.equals(other.pop)) {
            return false;
        }
        if ((this.popPassword == null) ? (other.popPassword != null) : !this.popPassword.equals(other.popPassword)) {
            return false;
        }
        if ((this.smtp == null) ? (other.smtp != null) : !this.smtp.equals(other.smtp)) {
            return false;
        }
        if ((this.smtpPassword == null) ? (other.smtpPassword != null) : !this.smtpPassword.equals(other.smtpPassword)) {
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
        return "entity.Mail{" + "id=" + id + ", mail=" + mail + ", pop=" + pop + ", popPassword=" + popPassword + ", smtp=" + smtp + ", smtpPassword=" + smtpPassword + ", observations=" + observations + ", sleeping=" + sleeping + ", idClient=" + idClient + '}';
    }
    
    @Override
    public String toString() {
        return this.mail;
    }
    
}
