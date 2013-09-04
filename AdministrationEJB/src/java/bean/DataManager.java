/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.CUserFacade;
import bean.facade.ClientFacade;
import bean.facade.DeviceFacade;
import bean.facade.FactureFacade;
import bean.facade.FilePathFacade;
import bean.facade.InterventionFacade;
import bean.facade.MailFacade;
import bean.facade.ServicesFacade;
import bean.facade.SoftwareFacade;
import bean.facade.TaskFacade;
import bean.log.ApplicationLogger;
import entity.CUser;
import entity.Client;
import entity.Device;
import entity.Facture;
import entity.FilePath;
import entity.Intervention;
import entity.Mail;
import entity.Services;
import entity.Software;
import entity.Task;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "dataManager")
@SessionScoped
public class DataManager implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Inject
    private UserLogin userLogin;
    
    @EJB
    private CUserFacade cUserFacade;
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private DeviceFacade deviceFacade;
    @EJB
    private FactureFacade factureFacade;
    @EJB
    private FilePathFacade filePathFacade;
    @EJB
    private InterventionFacade interventionFacade;
    @EJB
    private MailFacade mailFacade;
    @EJB
    private ServicesFacade servicesFacade;
    @EJB
    private SoftwareFacade softwareFacade;
    @EJB
    private TaskFacade taskFacade;
    
    private long cUserSize=-1;
    private long clientSize=-1;
    private long deviceSize=-1;
    private long factureSize=-1;
    private long filePathSize=-1;
    private long interventionSize=-1;
    private long mailSize=-1;
    private long servicesSize=-1;
    private long softwareSize=-1;
    private long taskSize=-1;
    private static final String SAVES_PATH="saves";
    
    public long getCUserSize()
    {
        if(this.cUserSize<0)
        {
            this.cUserSize=this.cUserFacade.findAll().size();
        }
        return this.cUserSize;
    }
    
    public long getClientSize()
    {
        if(this.clientSize<0)
        {
            this.clientSize=this.clientFacade.findAll().size();
        }
        return this.clientSize;
    }
    
    public long getDeviceSize()
    {
        if(this.deviceSize<0)
        {
            this.deviceSize=this.deviceFacade.findAll().size();
        }
        return this.deviceSize;
    }
    
    public long getFactureSize()
    {
        if(this.factureSize<0)
        {
            this.factureSize=this.factureFacade.findAll().size();
        }
        return this.factureSize;
    }
    
    public long getFilePathSize()
    {
        if(this.filePathSize<0)
        {
            this.filePathSize=this.filePathFacade.findAll().size();
        }
        return this.filePathSize;
    }
    
    public long getInterventionSize()
    {
        if(this.interventionSize<0)
        {
            this.interventionSize=this.interventionFacade.findAll().size();
        }
        return this.interventionSize;
    }
    
    public long getMailSize()
    {
        if(this.mailSize<0)
        {
            this.mailSize=this.mailFacade.findAll().size();
        }
        return this.mailSize;
    }
    
    public long getServicesSize()
    {
        if(this.servicesSize<0)
        {
            this.servicesSize=this.servicesFacade.findAll().size();
        }
        return this.servicesSize;
    }
    
    public long getSoftwareSize()
    {
        if(this.softwareSize<0)
        {
            this.softwareSize=this.softwareFacade.findAll().size();
        }
        return this.softwareSize;
    }
    
    public long getTaskSize()
    {
        if(this.taskSize<0)
        {
            this.taskSize=this.taskFacade.findAll().size();
        }
        return this.taskSize;
    }
    
    public boolean verifUserRights(boolean auto)
    {
        return (this.userLogin.getRights().equalsIgnoreCase(Utils.ADMIN_RIGHTS)||auto);
    }
    
    public boolean resetData(boolean auto)
    {
        if(!this.verifUserRights(auto))
        {
            Utils.displayMessage(FacesMessage.SEVERITY_FATAL,
                    "Droits insuffisants",
                    "Vos droits n'ont pas pu être vérifiés, merci de vous "
                    + "relogger avant de continuer");
            return false;
        }
        if(this.interventionFacade!=null)
        {
            for(Intervention intervention:this.interventionFacade.findAll())
            {
                this.interventionFacade.removeSilent(intervention, true);
            }
        }
        this.interventionSize=-1;
        if(this.taskFacade!=null)
        {
            for(Task task:this.taskFacade.findAll())
            {
                this.taskFacade.removeSilent(task, true);
            }
        }
        this.taskSize=-1;
        if(this.factureFacade!=null)
        {
            for(Facture facture:this.factureFacade.findAll())
            {
                this.factureFacade.removeSilent(facture, true);
            }
        }
        this.factureSize=-1;
        if(this.clientFacade!=null)
        {
            for(Client client:this.clientFacade.findAll())
            {
                CUser cuser=client.getIdUser();
                if(cuser!=null)
                {
                    client.setIdUser(null);
                }
                this.clientFacade.removeSilent(client, true);
            }
        }
        this.clientSize=-1;
        this.cUserSize=-1;
        if(this.deviceFacade!=null)
        {
            for(Device device:this.deviceFacade.findAll())
            {
                this.deviceFacade.removeSilent(device, true);
            }
        }
        this.deviceSize=-1;
        if(this.mailFacade!=null)
        {
            for(Mail mail:this.mailFacade.findAll())
            {
                this.mailFacade.removeSilent(mail, true);
            }
        }
        this.mailSize=-1;
        if(this.servicesFacade!=null)
        {
            for(Services services:this.servicesFacade.findAll())
            {
                this.servicesFacade.removeSilent(services, true);
            }
        }
        this.servicesSize=-1;
        if(this.softwareFacade!=null)
        {
            for(Software software:this.softwareFacade.findAll())
            {
                this.softwareFacade.removeSilent(software, true);
            }
        }
        this.softwareSize=-1;
        if(this.filePathFacade!=null)
        {
            for(FilePath filePath:this.filePathFacade.findAll())
            {
                this.filePathFacade.removeSilent(filePath, true);
            }
        }
        this.filePathSize=-1;
        if(!auto)
        {
            Utils.displayMessage(FacesMessage.SEVERITY_INFO,
                    "Données effacées",
                    "Les données ont bien été supprimées");
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public boolean loadData(boolean auto)
    {
        if(!this.verifUserRights(auto))
        {
            Utils.displayMessage(FacesMessage.SEVERITY_FATAL,
                    "Droits insuffisants",
                    "Vos droits n'ont pas pu être vérifiés, merci de vous "
                    + "relogger avant de continuer");
            return false;
        }
        List<CUser> cUsersList;
        List<Client> clientList;
        List<Device> deviceList;
        List<Facture> fatcureList;
        List<FilePath> filePathList;
        List<Intervention> interventionList;
        List<Mail> mailList;
        List<Services> servicesList;
        List<Software> softwareList;
        List<Task> taskList;
        
        try
        {
            ObjectInputStream ois = new ObjectInputStream(
                                          new FileInputStream("resources"+File.separator+
                                          SAVES_PATH+Files.separator()+"saves.dblst"));
            cUsersList=(List<CUser>) ois.readObject();
            clientList=(List<Client>) ois.readObject();
            deviceList=(List<Device>) ois.readObject();
            fatcureList=(List<Facture>) ois.readObject();
            filePathList=(List<FilePath>) ois.readObject();
            interventionList=(List<Intervention>) ois.readObject();
            mailList=(List<Mail>) ois.readObject();
            servicesList=(List<Services>) ois.readObject();
            softwareList=(List<Software>) ois.readObject();
            taskList=(List<Task>) ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException ex)
        {
            if(!auto)
            {
                Utils.displayMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur de chargement",
                        "Le fichier de sauvegarde n'a pas pu être trouvé");
            }
            ApplicationLogger.writeError("Impossible de trouver les données",
                    ex);
            return false;
        }
        catch (IOException ex)
        {
            if(!auto)
            {
                Utils.displayMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur de chargement",
                        "Le fichier de sauvegarde n'a pas pu être chargé");
            }
            ApplicationLogger.writeError("Impossible de charger les données",
                    ex);
            return false;
        }
        catch (ClassNotFoundException ex)
        {
            if(!auto)
            {
                Utils.displayMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur de chargement",
                        "Le fichier de sauvegarde n'a pas pu être lu");
            }
            ApplicationLogger.writeError("Impossible de lire les données",
                    ex);
            return false;
        }
        if(!this.resetData(true))
        {
            return false;
        }
        if(filePathList!=null&&this.filePathFacade!=null)
        {
            for(FilePath filePath:filePathList)
            {
                try
                {
                    this.filePathFacade.createSilent(filePath, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(filePath);
                    fullString=fullString==null?filePath.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(fatcureList!=null&&this.factureFacade!=null)
        {
            for(Facture facture:fatcureList)
            {
                try
                {
                    this.factureFacade.createSilent(facture, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(facture);
                    fullString=fullString==null?facture.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(clientList!=null&&this.clientFacade!=null)
        {
            for(Client client:clientList)
            {
                client.setCUserList(null);
                client.setDeviceList(null);
                client.setMailList(null);
                client.setServicesList(null);
                client.setSoftwareList(null);
                client.setTaskList(null);
                CUser interlocuteur=client.getIdUser();
                client.setIdUser(null);
                for(FilePath filePath:this.filePathFacade.findAll())
                {
                    if(client.getIdFilePath().equals(filePath))
                    {
                        client.setIdFilePath(filePath);
                        break;
                    }
                }
                try
                {
                    this.clientFacade.createSilent(client, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(client);
                    fullString=fullString==null?client.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
                if(interlocuteur!=null)
                {
                    interlocuteur.setClientList(null);
                    interlocuteur.setTaskList(null);
                    for(FilePath filePath:this.filePathFacade.findAll())
                    {
                        if(interlocuteur.getIdFilePath().equals(filePath))
                        {
                            interlocuteur.setIdFilePath(filePath);
                            break;
                        }
                    }
                    client.setIdUser(interlocuteur);
                    this.clientFacade.editSilent(client, true);
                }
            }
        }
        if(cUsersList!=null&&this.cUserFacade!=null)
        {
            for(CUser cUser:cUsersList)
            {
                if(!this.cUserFacade.findAll().contains(cUser))
                {
                    for(Client client:this.clientFacade.findAll())
                    {
                        if(cUser.getIdClient().equals(client))
                        {
                            cUser.setIdClient(client);
                            break;
                        }
                    }
                    for(FilePath filePath:this.filePathFacade.findAll())
                    {
                        if(cUser.getIdFilePath().equals(filePath))
                        {
                            cUser.setIdFilePath(filePath);
                            break;
                        }
                    }
                    cUser.setClientList(null);
                    cUser.setTaskList(null);
                    try
                    {
                        this.cUserFacade.createSilent(cUser, true);
                    }
                    catch(EntityExistsException ex)
                    {
                        String fullString=Utils.getFullString(cUser);
                        fullString=fullString==null?cUser.toString():fullString;
                        ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                                + "existe déjà", null);
                    }
                }
            }
        }
        if(deviceList!=null&&this.deviceFacade!=null)
        {
            for(Device device:deviceList)
            {
                for(Client client:this.clientFacade.findAll())
                {
                    if(device.getIdClient().equals(client))
                    {
                        device.setIdClient(client);
                        break;
                    }
                }
                device.setIdFilePath(null);
                device.setTaskList(null);
                try
                {
                    this.deviceFacade.createSilent(device, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(device);
                    fullString=fullString==null?device.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(mailList!=null&&this.mailFacade!=null)
        {
            for(Mail mail:mailList)
            {
                for(Client client:this.clientFacade.findAll())
                {
                    if(mail.getIdClient().equals(client))
                    {
                        mail.setIdClient(client);
                        break;
                    }
                }
                try
                {
                    this.mailFacade.createSilent(mail, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(mail);
                    fullString=fullString==null?mail.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(servicesList!=null&&this.servicesFacade!=null)
        {
            for(Services services:servicesList)
            {
                for(Client client:this.clientFacade.findAll())
                {
                    if(services.getIdClient().equals(client))
                    {
                        services.setIdClient(client);
                        break;
                    }
                }
                try
                {
                    this.servicesFacade.createSilent(services, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(services);
                    fullString=fullString==null?services.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(softwareList!=null&&this.softwareFacade!=null)
        {
            for(Software software:softwareList)
            {
                for(Client client:this.clientFacade.findAll())
                {
                    if(software.getIdClient().equals(client))
                    {
                        software.setIdClient(client);
                        break;
                    }
                }
                software.setIdFilePath(null);
                try
                {
                    this.softwareFacade.createSilent(software, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(software);
                    fullString=fullString==null?software.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(taskList!=null&&this.taskFacade!=null)
        {
            for(Task task:taskList)
            {
                for(Client client:this.clientFacade.findAll())
                {
                    if(task.getIdClient().equals(client))   
                    {
                        task.setIdClient(client);
                        break;
                    }
                }
                if(task.getIdDevice()!=null)
                {
                    for(Device device:this.deviceFacade.findAll())
                    {
                        if(task.getIdDevice().equals(device))
                        {
                            task.setIdDevice(device);
                            break;
                        }
                    }
                }
                if(task.getIdUser()!=null)
                {
                    for(CUser cUser:this.cUserFacade.findAll())
                    {
                        if(task.getIdUser().equals(cUser))
                        {
                            task.setIdUser(cUser);
                            break;
                        }
                    }
                }
                task.setInterventionList(null);
                try
                {
                    this.taskFacade.createSilent(task, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(task);
                    fullString=fullString==null?task.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(interventionList!=null&&this.interventionFacade!=null)
        {
            for(Intervention intervention:interventionList)
            {
                for(Task task:this.taskFacade.findAll())
                {
                    if(intervention.getIdTask().equals(task))
                    {
                        intervention.setIdTask(task);
                        break;
                    }
                }
                intervention.setIdFacture(null);
                try
                {
                    this.interventionFacade.createSilent(intervention, true);
                }
                catch(EntityExistsException ex)
                {
                    String fullString=Utils.getFullString(intervention);
                    fullString=fullString==null?intervention.toString():fullString;
                    ApplicationLogger.displayError("L'objet \""+fullString+"\" "
                            + "existe déjà", null);
                }
            }
        }
        if(!auto)
        {
            Utils.displayMessage(FacesMessage.SEVERITY_INFO,
                    "Données chargées",
                    "Les données ont bien été chargées depuis le fichier de "
                    + "sauvegarde");
        }
        return true;
    }
    
    public boolean saveData(boolean auto)
    {
        if(!this.verifUserRights(auto))
        {
            Utils.displayMessage(FacesMessage.SEVERITY_FATAL,
                    "Droits insuffisants",
                    "Vos droits n'ont pas pu être vérifiés, merci de vous "
                    + "relogger avant de continuer");
            return false;
        }
        ObjectOutputStream oos;
        try
        {
            Files.createIfNotExists(new File(Utils.getResourcesPath()+
                    SAVES_PATH+Files.separator()+"saves.dblst"), false);
            oos = new ObjectOutputStream(
                    new FileOutputStream("resources"+File.separator+
                    SAVES_PATH+Files.separator()+"saves.dblst"));
            oos.writeObject(this.cUserFacade.findAll());
            oos.writeObject(this.clientFacade.findAll());
            oos.writeObject(this.deviceFacade.findAll());
            oos.writeObject(this.factureFacade.findAll());
            oos.writeObject(this.filePathFacade.findAll());
            oos.writeObject(this.interventionFacade.findAll());
            oos.writeObject(this.mailFacade.findAll());
            oos.writeObject(this.servicesFacade.findAll());
            oos.writeObject(this.softwareFacade.findAll());
            oos.writeObject(this.taskFacade.findAll());
            oos.close();
        }
        catch (IOException ex)
        {
            if(!auto)
            {
                Utils.displayMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur de sauvegarde",
                        "Erreur lors de la création du fichier de "
                        + "sauvegarde des données");
            }
            ApplicationLogger.writeError("Impossible de sauvegarder les données",
                    ex);
            return false;
        }
        if(!auto)
        {
            Utils.displayMessage(FacesMessage.SEVERITY_INFO,
                    "Données sauvegardées",
                    "Les données ont bien été sauvegardées dans le fichier de "
                    + "sauvegarde");
        }
        return true;
    }
}
