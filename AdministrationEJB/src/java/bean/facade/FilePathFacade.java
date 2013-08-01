/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.ApplicationLogger;
import bean.Utils;
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
    
    @Override
    public void create(FilePath entity)
    {
        getEntityManager().persist(entity);
        String details=Utils.getFullString(entity);
        details=details!=null?details:entity.toString();
        ApplicationLogger.writeWarning("Création de l'entité de la classe \""+
                FilePath.class.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+FilePath.class.getName()+"\": \""+
                details+"\"");
    }
    
    @Override
    public void edit(FilePath entity)
    {
        getEntityManager().merge(entity);
        String details=Utils.getFullString(entity);
        details=details!=null?details:entity.toString();
        ApplicationLogger.addSmallSep();
        ApplicationLogger.writeWarning("Modification de l'entité de la classe \""+
                FilePath.class.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+FilePath.class.getName()+"\": \""+
                details+"\"");
        ApplicationLogger.addSmallSep();
    }
    
    @Override
    public void remove(FilePath entity)
    {
        FilePath temp=entity;
        String details=Utils.getFullString(temp);
        details=details!=null?details:temp.toString();
        getEntityManager().remove(getEntityManager().merge(entity));
        ApplicationLogger.writeWarning("Suppression de l'entité de la classe \""+
                FilePath.class.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+FilePath.class.getName()+"\": \""+
                details+"\"\r");
    }
    
}
