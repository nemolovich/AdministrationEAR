/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.struct;

import bean.ApplicationLogger;
import bean.City;
import bean.Files;
import bean.Utils;
import bean.facade.FilePathFacade;
import bean.facade.abstracts.AbstractFacade;
import bean.view.filteredSelection.EntitySleepingSelection;
import entity.FilePath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntityView<C,F extends AbstractFacade<C>> extends EntitySleepingSelection<C>
{
    private static final long serialVersionUID = 1L;
    private C entity;
    private Class<C> entityClass;
    private String webFolder=null;
    private F entityFacade;
    private boolean creating=false;
    private boolean editing=false;
    private List<String> cityList;
    private List<String> postalCodeList;
    
    @EJB
    private FilePathFacade filePathFacade;
    
    public EntityView()
    {
    }
    
    public EntityView(Class<C> entityClass,String webFolder)
    {
        super(entityClass);
        City.load();
        this.cityList=City.getCityNames();
        this.postalCodeList=City.getPostalCodes();
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
        this.entityClass=entityClass;
    }
    
    public List<String> cityComplete(String query)
    {
        String value = City.getNormalizedCity(query.toLowerCase());
        List<String> result = new ArrayList<String>();
        List<String> like = City.likeCity(value);
        List<String> begin = City.beginWith(value);
        if(like!=null)
        {
            result.addAll(like);
        }
        if(begin!=null)
        {
            result.addAll(begin);
        }
        Collections.sort(result);
        return result;
    }
    
    public List<String> postalCodeComplete(String query)
    {
        List<String> result = new ArrayList<String>();   
        for(String postalCode:this.postalCodeList)
        {
            if(postalCode.startsWith(query))
            {
                result.add(postalCode);
            }
        }
        Collections.sort(result);
        return result;
    }
    
    public Class<C> getEntityClass()
    {
        return this.entityClass;
    }
    
    @Override
    public List<C> getFullList()
    {
        return this.findAll();
    }

    public boolean isEditing()
    {
        return editing;
    }

    public void setEditing(boolean editing)
    {
        this.editing = editing;
    }
    
    public boolean isCreating()
    {
        return this.creating;
    }

    public void setCreating(boolean creating)
    {
        this.creating = creating;
    }
    
    protected void setWebFolder(String webFolder)
    {
        this.webFolder=webFolder;
    }
    
    protected String getWebFolder()
    {
        return this.webFolder;
    }
    
    public String create()
    {
        return this.createSilent(false);
    }
    
    public String createSilent(boolean silent)
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.createSilent(this.entity, silent);
        this.setEntityPathFilePath();
        return this.webFolder+"list";
    }
    
    public String delete()
    {
        return this.deleteSilent(false);
    }
    
    public String deleteSilent(boolean silent)
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.removeSilent(this.entity, silent);
        this.removeFilePath();
        this.entity=null;
        return this.webFolder+"list";
    }
    
    public String update()
    {
        return this.updateSilent(false);
    }
    
    public String updateSilent(boolean silent)
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.editSilent(this.entity, silent);
        return this.webFolder+"list";
    }
    
    public String cancelCreate()
    {
        this.creating = false;
        this.editing = false;
        this.entity = null;
        return this.webFolder+"view";
    }
    
    protected void removeFilePath()
    {
        FilePath filePath=this.getEntityFilePath();
        if(filePath!=null)
        {
            File path=new File(Utils.getResourcesPath()+Utils.getUploadsPath()+
                    filePath.getFilePath()+File.separator);
            try
            {
                if(path.exists())
                {
                    Files.deleteContent(path);
                    Files.deleteFolder(path);
                }
                this.filePathFacade.remove(filePath);
            }
            catch (FileNotFoundException ex)
            {
                ApplicationLogger.writeError("Le dossier \""+path.getPath()+
                        "\" n'a pas pu être supprimé", ex);
            }
        }
    }
    
    public C checkSingle(C[] entities)
    {
        if(entities!=null&&entities.length!=1)
        {
            FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Sélection invalide",
                    "Vous devez sélectionner un et un seul élément "
                    + "pour effectuer cette tâche");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        return entities[0];
    }
    
    public boolean setEntityFilePath(FilePath filePath)
    {
        try
        {
            Method m=entity.getClass().getMethod("setIdFilePath", FilePath.class);
            Utils.callMethod(m, "définition du répertoire de stockage", entity,
                    filePath);
            return true;
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"setIdFilePath\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"",
                    null);
        }
        return false;
    }
    
    public FilePath getEntityFilePath()
    {
        if(this.entity==null)
        {
            return null;
        }
        try
        {
            Method m=this.entity.getClass().getMethod("getIdFilePath");
            FilePath filePath=(FilePath)Utils.callMethod(m,
                    "récupération du répertoire de stockage", entity);
            if(filePath!=null)
            {
                return filePath;
            }
            else
            {
                filePath=new FilePath(this.entity.getClass().getSimpleName()+
                        File.separator+String.format("%04d", this.getId()));
                this.filePathFacade.create(filePath);
                this.setEntityFilePath(filePath);
                this.update();
                return filePath;
            }
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"getIdFilePath\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"",
                    null);
        }
        return null;
    }
    
    public void setEntityPathFilePath()
    {
        FilePath filePath=this.getEntityFilePath();
        Integer id=this.getId();
        if(filePath!=null)
        {
            String message="Modification du chemin \""+filePath.getFilePath()+
                    "\" pour l'instance de la classe \""+this.entityClass.getName()+"\"";
            ApplicationLogger.writeInfo(message);
            String path=filePath.getFilePath();
            path=path.substring(0, path.lastIndexOf(File.separator));
            path+=File.separator+String.format("%04d", id);
            try
            {
                Files.move(new File(Utils.getResourcesPath()+Utils.getUploadsPath()+
                        filePath.getFilePath()),
                        new File(Utils.getResourcesPath()+Utils.getUploadsPath()+path));
                filePath.setFilePath(path);
                this.filePathFacade.edit(filePath);
            }
            catch (IOException ex)
            {
                ApplicationLogger.writeError("Erreur lors du déplacement du "
                        + "dossier \""+Utils.getResourcesPath()+Utils.getUploadsPath()+
                        filePath.getFilePath()+"\"", ex);
            }
        }
    }
    
    public String getEntityPathFilePath()
    {
        if(this.getEntityFilePath()!=null)
        {
            return this.getEntityFilePath().getFilePath();
        }
        return null;
    }

    public String entityView(C entity)
    {
        this.creating = false;
        this.editing = false;
        this.entity = entity;
        return this.webFolder+"view";
    }
    
    public String entityUpdate(C entity)
    {
        this.creating = false;
        this.editing = true;
        this.entity = entity;
        return this.webFolder+"update";
    }
    
    public String entityParameter(C entity)
    {
        this.creating = false;
        this.editing = true;
        this.entity = entity;
        return this.webFolder+"parameters";
    }
    
    public String entityDelele(C entity)
    {
        return this.entitySilentDelete(entity, false);
    }
    
    public String entitySilentDelete(C entity, boolean silent)
    {
        this.creating = false;
        this.editing = false;
        this.entity = entity;
        this.setFacade();
        this.entityFacade.removeSilent(entity, silent);
        this.removeFilePath();
        this.entity = null;
        return this.webFolder+"list";
    }
    
    public String entitySleep(C entity)
    {
        return this.entitySilentSleep(entity, false);
    }
    
    public String entitySilentSleep(C entity, boolean silent)
    {
        try
        {
            Method m=entity.getClass().getMethod("setSleeping", Boolean.class);
            Utils.callMethod(m, "mise en veille de la donnée", entity, true);
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"setSleeping\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"", ex);
        }
        this.entityFacade.editSilent(entity, silent);
        return this.webFolder+"list";
    }
    
    public String entityWake(C entity)
    {
        return this.entitySilentWake(entity, false);
    }
    
    public String entitySilentWake(C entity, boolean silent)
    {
        try
        {
            Method m=entity.getClass().getMethod("setSleeping", Boolean.class);
            Utils.callMethod(m, "réactivation de la donnée", entity, false);
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"setSleeping\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"", ex);
        }
        this.entityFacade.editSilent(entity, silent);
        return this.webFolder+"list";
    }
    
    public String entityCreate()
    {
        this.creating = true;
        this.editing = false;
        String message="Création d'une entité de la classe \""+this.entityClass.getName()+"\"";
        ApplicationLogger.writeInfo(message);
        try
        {
            this.entity = this.entityClass.newInstance();
        }
        catch (InstantiationException ex)
        {
            ApplicationLogger.writeError("Impossible d'instancier un objet de la"
                    + " classe \""+this.entityClass.getName()+"\"", ex);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.writeError("Droits refusés pour l'instanciation"
                    + " d'un objet de la classe \""+this.entityClass.getName()+"\"", ex);
        }
        message="Vérification de l'existance d'une association de fichiers pour "
                + "la classe \""+this.entityClass.getName()+"\"";
        ApplicationLogger.writeInfo(message);
        String tempFolder=this.entityClass.getSimpleName()+
                File.separator+FilePath.TEMP_FOLDER;
        FilePath filePath=this.filePathFacade.getFilePath(tempFolder);
        boolean create=false;
        if(filePath==null)
        {
            filePath=new FilePath(tempFolder);
            create=true;
        }
        if(this.setEntityFilePath(filePath))
        {
            message="Association d'une entity \""+FilePath.class.getName()+
                    "\" pour l'instance de la classe \""+this.entityClass.getName()+"\"";
            ApplicationLogger.writeInfo(message);
            if(create)
            {
                this.filePathFacade.create(filePath);
            }
            this.setEntityFilePath(filePath);
        }
        else
        {
            message="Impossible d'associer des fichiers pour "
                    + "la classe \""+this.entityClass.getName()+"\"";
            ApplicationLogger.writeError(message, null);
        }
        return this.webFolder+"create";
    }
    
    public String setInstance(C entity)
    {
        this.entity = entity;
        return null;
    }
    
    public C getInstance()
    {
        return this.entity;
    }

    public void setEntityFacade(F entityFacade)
    {
        this.entityFacade=entityFacade;
    }

    protected F getEntityFacade()
    {
        return this.entityFacade;
    }
    
    public List<C> findAll()
    {
        this.setFacade();
        List<C> result=this.entityFacade.findAll();
        if(result==null)
        {
            result=new ArrayList<C>();
        }
        return result;
    }
    
    /**
     * Toutes les entités sont censées avoir un champs 'Id', on peut donc
     * utiliser la réflexion pour récupérer ce que renvoi cette methode sur
     * l'entité sur laquelle on travaille
     * @return {@link Integer} - L'identifiant de l'entité
     */
    public Integer getId()
    {
        Integer id = -1;
        try
        {
            id=(Integer) Utils.callMethod(this.entityClass.getMethod("getId"),
                    "récupération de l'identifiant", this.entity);
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode de récupération de"
                    + " l'identifiant pour la class \""+entityClass.getName()+"\""
                    + " n'a pas été trouvée", ex);
        }
        catch (SecurityException ex)
        {
            ApplicationLogger.writeError("La méthode de récupération de"
                    + " l'identifiant pour la class \""+entityClass.getName()+"\""
                    + " n'est pas accessible", ex);
        }
        return id;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Les methodes abstraites">
    /**
     * Insérer le code:
     * <code>
     * super.entityFacade=this.[VotreEJBFacade];
     * </code>
     */
    public abstract void setFacade();
    /**
     * Insérer le code:
     * <code>
     * return super.findAll();
     * </code>
     */
    public abstract List<C> getEntries();
    /**
     * Insérer le code:
     * <code>
     * return super.getInstance();
     * </code>
     */
    public abstract C getEntity();
    /**
     * Insérer le code:
     * <code>
     * super.setInstance(C entity);
     * </code>
     * @param entity 
     */
    public abstract void setEntity(C entity);
    /**
     * Renvoi le message d'avertissement avant la suppression
     * de cette entité
     * @param entity {@link C} - L'entité à supprimer
     * @return {@link String}, Le message d'avertissement
     */
    public abstract String getDeleteMessage(C entity);
    
    // </editor-fold>
}
