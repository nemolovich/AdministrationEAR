/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view;

import bean.facade.ServicesFacade;
import bean.view.struct.EmbeddedDataListView;
import entity.Client;
import entity.Services;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "servicesView")
@SessionScoped
public class ServicesView extends EmbeddedDataListView<Client, Services, ServicesFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private ServicesFacade servicesFacade;
    
    public ServicesView() throws NoSuchMethodException
    {
        super(Services.class,"services",
                Services.class.getMethod("setIdClient",
                                        new Class<?>[]{Client.class}));
    }
    
    @Override
    public void setEntity(Services entity)
    {
        super.setInstance(entity);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.servicesFacade);
    }

    @Override
    public List<Services> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Services getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Services entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                    + " ce service ("+entity.getTitle()
                    + " id="+entity.getId()+"). Cette action est irreversible,"
                    + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<Services> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous"
                + " les services sélectionnés (";
            boolean first=true;
            for(Services entity:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=entity.getTitle()+" id="+entity.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }
}
