/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection.struct;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
    
    public boolean isSingleSelected()
    {
        return this.multiSelection!=null&&this.multiSelection.length==1;
    }
    
    public boolean checkEmptySelection()
    {
        if(this.multiSelection==null||this.multiSelection.length==0)
        {
            FacesMessage message=new FacesMessage("Sélection invalide",
                    "Vous devez sélectionner au moins un élément "
                    + "pour effectuer cette tâche");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return true;
        }
        return false;
    }

    public void setMultiDataModel(List<C> list)
    {
        this.multiDataModel = new EntityDataModel<C>(list){};
    }

    public C[] getMultiSelection()
    {
        return multiSelection;
    }

    public void setMultiSelection(C[] multiSelection)
    {
        if(multiSelection.length>0)
        {
            this.singleSelection = multiSelection[0];
        }
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