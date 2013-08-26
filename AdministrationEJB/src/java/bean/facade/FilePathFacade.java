/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractFacade;
import entity.FilePath;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class FilePathFacade extends AbstractFacade<FilePath> {
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public FilePathFacade() {
        super(FilePath.class);
    }
    
    /**
     * Renvoi l'entité qui contient le chemin passé en paramère
     * @param path {@link String} - Chemin à chercher
     * @return {@FilePath} - L'entité qui contient le chemin, <code>null</code>
     * si le chemin n'a pas été trouvé
     */
    public FilePath getFilePath(String path)
    {
        for(FilePath filePath:this.findAll())
        {
            if(filePath.getFilePath().equals(path))
            {
                return filePath;
            }
        }
        return null;
    }
    
    @Override
    public void create(FilePath entity)
    {
        super.createSilent(entity, true);
    }
    
    @Override
    public void edit(FilePath entity)
    {
        super.editSilent(entity, true);
    }
    
    @Override
    public void remove(FilePath entity)
    {
        super.removeSilent(entity, true);
    }
    
}
