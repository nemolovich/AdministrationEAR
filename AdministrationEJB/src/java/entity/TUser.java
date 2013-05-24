/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Windows 7
 */
@Entity
@Table(name = "T_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUser.findAll", query = "SELECT t FROM TUser t"),
    @NamedQuery(name = "TUser.findById", query = "SELECT t FROM TUser t WHERE t.id = :id"),
    @NamedQuery(name = "TUser.findByMail", query = "SELECT t FROM TUser t WHERE t.mail = :mail"),
    @NamedQuery(name = "TUser.findByName", query = "SELECT t FROM TUser t WHERE t.name = :name"),
    @NamedQuery(name = "TUser.findByFirstname", query = "SELECT t FROM TUser t WHERE t.firstname = :firstname"),
    @NamedQuery(name = "TUser.findByRights", query = "SELECT t FROM TUser t WHERE t.rights = :rights"),
    @NamedQuery(name = "TUser.findByPassword", query = "SELECT t FROM TUser t WHERE t.password = :password")})
public class TUser implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final String UNKNOWN_RIGHTS = "UNKNOWN";
    public static final String USER_RIGHTS = "USER";
    public static final String ADMIN_RIGHTS = "ADMIN";
    private static final List<String> RIGHTS = Arrays.asList(
            TUser.UNKNOWN_RIGHTS,
            TUser.USER_RIGHTS,
            TUser.ADMIN_RIGHTS);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "MAIL")
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,
    message = "Le nom doit contenir entre 1 et 30 caractères")
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,
    message = "Le prénom doit contenir entre 1 et 30 caractères")
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RIGHTS")
    private String rights = TUser.UNKNOWN_RIGHTS;

    public TUser() {
    }
    
    public TUser(TUser tUser)
    {
        this.id=tUser.id;
        this.mail=tUser.mail;
        this.name=tUser.name;
        this.firstname=tUser.firstname;
        this.password=tUser.password;
        this.rights=tUser.rights;
    }

    public TUser(Integer id) {
        this.id = id;
    }

    public TUser(Integer id, String mail, String name, String firstname, String password, String rights) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.firstname = firstname;
        this.password = password;
        this.rights = rights;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }
    
    public String getFieldValue(String fieldName)
    {
        try
        {
            Method m=TUser.class.getMethod("get"+fieldName);
            String res=(String) m.invoke((Object)this);
            return res;
        }
        catch (NoSuchMethodException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SecurityException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalArgumentException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvocationTargetException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }
    
    public void setRights(String rights)
    {
        if(TUser.RIGHTS.contains(rights))
        {
            this.rights = rights;
        }
        else
        {
            System.err.println("Les droits ne sont pas corrects");
        }
    }
    
    public String getRights() {
        return this.rights;
    }
    
    public List<String> getEnumRights()
    {
        return TUser.RIGHTS;
    }
    
    public void setEncryptedPassword(byte[] md5)
    {
        this.password=new String(md5/*,"UTF-8"*/);
    }
    
    public void setPassword(String password)
    {
        if(password.isEmpty())
        {
            Logger.getLogger(TUser.class.getName()).log(Level.WARNING, "Utilisation de l'ancien mot de passe");
            return;
        }
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(TUser.class.getName()).log(Level.SEVERE, null, ex);
            this.password="nopass";
            return;
        }
        md.update(password.getBytes(/*"UTF-8"*/));
        byte[] md5 = md.digest();
        this.password=new String(md5/*,"UTF-8"*/);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.mail != null ? this.mail.hashCode() : 0);
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.firstname != null ? this.firstname.hashCode() : 0);
        hash = 89 * hash + (this.rights != null ? this.rights.hashCode() : 0);
        hash = 89 * hash + (this.password != null ? this.password.hashCode() : 0);
        return hash;
    }

    /**
     * On créer un equals personnalisé pour pouvoir comparer cette classe avec
     * la classe {@link User}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        // On utilise ici isAssignable from car la classe User 
        // est un extension de la classe TUser
        if (obj.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }
        final TUser other = (TUser) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.firstname == null) ? (other.firstname != null) : !this.firstname.equals(other.firstname)) {
            return false;
        }
        if ((this.rights == null) ? (other.rights != null) : !this.rights.equals(other.rights)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entity.TUser{" + "id=" + id + ", mail=" + mail + ", name=" + name + ", firstname=" + firstname + ", rights=" + rights + '}';
    }
    
}