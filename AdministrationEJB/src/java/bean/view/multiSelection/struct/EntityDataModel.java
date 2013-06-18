/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection.struct;

import bean.Utils;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntityDataModel<C> extends ListDataModel<C>
    implements SelectableDataModel<C>
{
    
    private Method getId;
    
    public EntityDataModel(Class<C> entityClass)
    {
        this(null,entityClass);
    }

    public EntityDataModel(List<C> list, Class<C> entityClass)
    {
        super(list);
        try
        {
            this.getId=entityClass.getMethod("getId");
        }
        catch (NoSuchMethodException ex)
        {
            Logger.getLogger(EntityDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SecurityException ex)
        {
            Logger.getLogger(EntityDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Object getRowKey(C c)
    {
        Integer id=(Integer) Utils.callMethod(getId, "récupération de l'identifiant", c);
//        System.err.println("On m'a appellé?");
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C getRowData(String rowKey)
    {
        Integer rowId=Integer.parseInt(rowKey);
//        System.out.println("Row: '"+rowId+"'");
        for(C c:(List<C>)this.getWrappedData())
        {
//            System.out.println("toString: '"+Utils.getDataModelFormat(c.toString())+"'");
            if(rowId==Utils.callMethod(getId, "récupération de l'identifiant", c))
            {
                return c;
            }
        }
        return null;
    }
}
