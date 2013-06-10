/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.facade.abstracts.AbstractEmbdedDataList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EmbdedDataListView<C,O,F extends AbstractEmbdedDataList<C,O>> extends EntityView<O,F>
{
    private Method setReferenceMethod;

    public EmbdedDataListView() {
    }    

    public EmbdedDataListView(Class<O> entityClass,String webFolder,
            Method setReferenceMethod)
    {
        super(entityClass, webFolder);
        this.setReferenceMethod = setReferenceMethod;
    }
    
    public String entityCreate(C entity)
    {
        try
        {
            super.entityCreate();
            Logger.getLogger(EmbdedDataListView.class.getName()).log(Level.INFO,
                    "Appel de la méthode de reférencement");
            this.setReferenceMethod.invoke(this.getInstance(),entity);
            return "create";
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(EmbdedDataListView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalArgumentException ex)
        {
            Logger.getLogger(EmbdedDataListView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvocationTargetException ex)
        {
            Logger.getLogger(EmbdedDataListView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String create(C entity)
    {
        this.setFacade();
        super.getEntityFacade().updateDataList(entity,this.getInstance());
        return null;
    }
    
    @Override
    public abstract void setFacade();
}
