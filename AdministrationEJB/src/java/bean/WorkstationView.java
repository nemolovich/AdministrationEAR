/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.WorkstationFacade;
import bean.viewStruct.EntityView;
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
public class WorkstationView extends EntityView<Workstation, WorkstationFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private WorkstationFacade workstationFacade;
    
    public WorkstationView()
    {
        super(Workstation.class,"workstation");
    }
    
    public String entityCreate(Client client)
    {
        super.entityCreate();
        this.getInstance().setIdClient(client);
        return "create";
    }
    
    public String create(Client entity)
    {
        this.workstationFacade.updateWorkstationList(entity,this.getInstance());
        return super.create();
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