/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.ClientFacade;
import bean.viewStruct.EntityView;
import entity.Client;
import java.util.ArrayList;
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
    public List<String> getList()
    {
        List<String> list=new ArrayList<String>();
        for(Client client:this.getEntries())
        {
            list.add(client.getName());
        }
        return list;
    }
}