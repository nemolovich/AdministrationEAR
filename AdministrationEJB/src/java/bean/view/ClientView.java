/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.ClientFacade;
import bean.view.struct.EntityView;
import entity.Client;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
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
    private SelectItem[] states;
    private final Object[][] stateNames={{"Les deux",""},{"Actif",false},{"Inactif",true}};
    private List<Client> filteredEntities;
    private boolean firstView=true;
    
    public ClientView()
    {
        super(Client.class,"client");
        this.states=this.createFilterOptions(stateNames);
    }
    
    private List<Client> getWakeEntities()
    {
        this.setFacade();
        List<Client> res=new ArrayList<Client>();
        for(Client entity:this.findAll())
        {
            if(!entity.getSleeping())
            {
                res.add(entity);
            }
        }
        return res;
    }
    
    private SelectItem[] createFilterOptions(Object[][] data)
    {  
        SelectItem[] options = new SelectItem[data.length];  
  
        for(int i = 0; i < data.length; i++)
        {
            options[i] = new SelectItem(data[i][1].toString(), (String)data[i][0]);
        }
        return options;
    }
  
    public SelectItem[] getSleepingOptions()
    {  
        return this.states;  
    }
    
    public List<Client> getFilteredEntities()
    {
        if(this.firstView)
        {
            this.setFilteredEntities(this.getWakeEntities());
            this.firstView=false;
        }
        return this.filteredEntities;
    }  
  
    public void setFilteredEntities(List<Client> filteredEntities)
    {  
        this.filteredEntities = filteredEntities;  
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