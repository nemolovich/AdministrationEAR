/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import bean.Utils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "FILE_PATH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilePath.findAll", query = "SELECT f FROM FilePath f"),
    @NamedQuery(name = "FilePath.findById", query = "SELECT f FROM FilePath f WHERE f.id = :id"),
    @NamedQuery(name = "FilePath.findByFilePath", query = "SELECT f FROM FilePath f WHERE f.filePath = :filePath")})
public class FilePath implements Serializable {
    @OneToMany(mappedBy = "idFilePath")
    private List<Workstation> workstationList;
    @OneToMany(mappedBy = "idFilePath")
    private List<Client> clientList;
    @OneToMany(mappedBy = "idFilePath")
    private List<CUser> cUserList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 250)
    @Column(name = "FILE_PATH")
    private String filePath="./";
    public static final String TEMP_FOLDER="temp";
    public static final String TEXT_FILES_EXTENSIONS="pdf|doc?x|txt|odt";

    public FilePath() {
    }

    public FilePath(Integer id) {
        this.id = id;
    }

    public FilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getId() {
        return this.id==null?-1:this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public boolean isEmptyFilePath()
    {
        File path=new File(Utils.getResourcesPath()+Utils.getUploadsPath()+
                this.filePath+File.separator);
        return !(path.isDirectory()&&path.listFiles()!=null&&
                path.listFiles().length>0);
    }
    
    public List<File> getFilesInPath()
    {
        List<File> files = new ArrayList<File>();
        String path = Utils.getResourcesPath()+Utils.getUploadsPath()+
                this.filePath+File.separator;
        File[] list = new File(path).listFiles();
        if(list!=null)
        {
            for(int i=0;i<list.length;i++)
            {
                if(list[i].isFile())
                {
                    files.add(list[i]);
                }
            }
        }
        return files;
    }

    @XmlTransient
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    @XmlTransient
    public List<CUser> getCUserList() {
        return cUserList;
    }

    public void setCUserList(List<CUser> cUserList) {
        this.cUserList = cUserList;
    }

    @XmlTransient
    public List<Workstation> getWorkstationList() {
        return workstationList;
    }

    public void setWorkstationList(List<Workstation> workstationList) {
        this.workstationList = workstationList;
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
        if (!(object instanceof FilePath)) {
            return false;
        }
        FilePath other = (FilePath) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getFullString()
    {
        return "entity.FilePath{" + "workstationList=" + workstationList + ", clientList=" + clientList + ", cUserList=" + cUserList + ", id=" + id + ", filePath=" + filePath + '}';
    }
    
    @Override
    public String toString() {
        return "['"+this.filePath+"']";
    }
}
