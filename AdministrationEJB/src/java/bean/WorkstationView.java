/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.WorkstationFacade;
import bean.viewStruct.EmbdedDataListView;
import entity.Client;
import entity.Workstation;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "workstationView")
@SessionScoped
public class WorkstationView extends EmbdedDataListView<Client, Workstation, WorkstationFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private WorkstationFacade workstationFacade;
    
    public WorkstationView() throws NoSuchMethodException
    {
        super(Workstation.class,"workstation",
                Workstation.class.getMethod("setIdClient",
                                            new Class<?>[]{Client.class}));
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.workstationFacade);
    }

    @Override
    public List<Workstation> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Workstation getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Workstation entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " ce poste de travail ("+entity.toString()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Workstation workstation)
    {
        super.setInstance(workstation);
    }
}