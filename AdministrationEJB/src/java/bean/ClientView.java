/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.ClientFacade;
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
@Named(value = "clientView")
@SessionScoped
public class ClientView extends EntityView<Client, ClientFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private ClientFacade clientFacade;
    private boolean creating=false;
    
    public ClientView()
    {
        super(Client.class,"client");
    }

    public boolean getIsCreating()
    {
        return this.creating;
    }

    public void setIsCreating(boolean creating)
    {
        this.creating = creating;
    }
    
    @Override
    public String entityUpdate(Client entity)
    {
        this.creating=false;
        return super.entityUpdate(entity);
    }
    
    public String entityUpdate(Client entity, boolean creating)
    {
        this.creating=creating;
        return super.entityUpdate(entity);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.clientFacade);
    }

    @Override
    public List<Client> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Client getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Client entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cette société ("+entity.getName()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Client entity)
    {
        super.setInstance(entity);
    }
}