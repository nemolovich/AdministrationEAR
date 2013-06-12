/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.WorkstationFacade;
import bean.view.struct.EmbdedDataListView;
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
    public String deleteMessages(List<Workstation> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous les "
                + "postes de travails sélectionnés (";
            boolean first=true;
            for(Workstation workstation:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=workstation.getBrand()+" id="+workstation.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }

    @Override
    public void setEntity(Workstation workstation)
    {
        super.setInstance(workstation);
    }
}