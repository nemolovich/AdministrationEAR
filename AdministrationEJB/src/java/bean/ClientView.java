/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.ClientFacade;
import bean.viewStruct.EntityView;
import entity.Client;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Stage
 */
@Named(value = "clientView")
@SessionScoped
public class ClientView extends EntityView<Client, ClientFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private ClientFacade clientFacade;
    
    public ClientView()
    {
        super(Client.class,"client");
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
}