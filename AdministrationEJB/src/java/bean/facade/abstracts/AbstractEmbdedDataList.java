/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import bean.viewStruct.EmbdedDataListView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class AbstractEmbdedDataList<C,O> extends AbstractFacade<O>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;
    private Method getDataListMethod;
    private Method setDataListMethod;
    
    public AbstractEmbdedDataList(Class<O> objectClass,
            Method getDataListMethod, Method setDataListMethod)
    {
        super(objectClass);
        this.getDataListMethod = getDataListMethod;
        this.setDataListMethod = setDataListMethod;
    }
    
    @SuppressWarnings("unchecked")
    public void updateDataList(C entity, O add)
    {
        try
        {
            Logger.getLogger(AbstractEmbdedDataList.class.getName()).log(Level.INFO,
                    "Appel de la méthode de récupération des données");
            List<O> list=(List<O>) this.getDataListMethod.invoke(entity);
            list.add(add);
            Logger.getLogger(AbstractEmbdedDataList.class.getName()).log(Level.INFO,
                    "Appel de la méthode de paramétrage des données");
            this.setDataListMethod.invoke(entity,list);
            this.em.merge(entity);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(AbstractEmbdedDataList.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalArgumentException ex)
        {
            Logger.getLogger(AbstractEmbdedDataList.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvocationTargetException ex)
        {
            Logger.getLogger(AbstractEmbdedDataList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
