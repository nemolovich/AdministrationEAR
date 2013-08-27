/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view;

import bean.facade.CUserFacade;
import bean.view.struct.EmbdedDataListView;
import entity.CUser;
import entity.Client;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "cUserView")
@SessionScoped
public class CUserView extends EmbdedDataListView<Client, CUser, CUserFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private CUserFacade cUserFacade;
    
    public CUserView() throws NoSuchMethodException
    {
        super(CUser.class,"c_user",
                CUser.class.getMethod("setIdClient",
                                        new Class<?>[]{Client.class}));
    }
    
    public void setInterlocuteur(CUser user, Client client)
    {
        if(user==null||client==null)
        {
            return;
        }
        this.setInstance(user);
        client.setIdUser(user);
        super.update(client);
    }
    
    @Override
    public void setEntity(CUser entity)
    {
        super.setInstance(entity);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.cUserFacade);
    }

    @Override
    public List<CUser> getEntries()
    {
        return super.findAll();
    }

    @Override
    public CUser getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(CUser entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                    + " cet utilisateur ("+entity.getName()
                    + " id="+entity.getId()+"). Cette action est irreversible,"
                    + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<CUser> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous"
                + " les utilisateurs sélectionnés (";
            boolean first=true;
            for(CUser cuser:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=cuser.getName()+" id="+cuser.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }
}
