/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.multiSelection.struct;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Brian GOHIER
 */
public class EntityMultiView<C> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private C[] multiSelection;
    private C singleSelection;
    private EntityDataModel<C> multiDataModel;

    public EntityDataModel<C> getMultiDataModel()
    {
        return multiDataModel;
    }

    public void setMultiDataModel(List<C> list)
    {
        this.multiDataModel = new EntityDataModel(list){};
    }

    public C[] getMultiSelection()
    {
        return multiSelection;
    }

    public void setMultiSelection(C[] multiSelection)
    {
        this.multiSelection = multiSelection;
    }

    public C getSingleSelection()
    {
        return singleSelection;
    }

    public void setSingleSelection(C singleSelection)
    {
        this.singleSelection = singleSelection;
    }
}
