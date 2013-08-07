/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.ApplicationLogger;
import bean.facade.FilePathFacade;
import bean.view.struct.EntityView;
import entity.FilePath;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "filePathView")
@SessionScoped
public class FilePathView extends EntityView<FilePath, FilePathFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private FilePathFacade filePathFacade;
    
    public FilePathView()
    {
        super(FilePath.class,"file_path");
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.filePathFacade);
    }

    @Override
    public List<FilePath> getEntries()
    {
        this.purge();
        return super.findAll();
    }
    
    /**
     * Supprime les chemins inutiles
     */
    private void purge()
    {
        for(FilePath filePath:super.findAll())
        {
            if(filePath.getFilePath()!=null&&
                    filePath.getFilePath().endsWith(FilePath.TEMP_FOLDER))
            {
                super.getEntityFacade().remove(filePath);
            }
        }
    }

    @Override
    public FilePath getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(FilePath entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " ce dossier de fichier ("+entity.getFilePath()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(FilePath entity)
    {
        super.setInstance(entity);
    }
}