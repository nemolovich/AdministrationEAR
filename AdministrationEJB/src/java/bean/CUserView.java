/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.CUserFacade;
import bean.viewStruct.EntityView;
import entity.CUser;
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
public class CUserView extends EntityView<CUser, CUserFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private CUserFacade cUserFacade;
    
    public CUserView()
    {
        super(CUser.class,"c_user");
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
}
