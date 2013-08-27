/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view;

import bean.UserLogin;
import bean.Utils;
import bean.facade.TUserFacade;
import bean.view.struct.EntityView;
import entity.TUser;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "tUserView")
@SessionScoped
public class TUserView extends EntityView<TUser, TUserFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private TUserFacade tUserFacade;

    public TUserView()
    {
        super(TUser.class,"");
        super.setWebFolder("/restricted/admin/user/");
    }
    
    @Override
    public String entityUpdate(TUser entity)
    {
        super.entityUpdate(entity);
        return "/restricted/user/user/update";
    }
    
    public String entityUpdate(TUser entity, UserLogin currentUser)
    {
        super.entityUpdate(entity);
        if(currentUser.getRights().equals(Utils.ADMIN_RIGHTS))
        {
            return "/restricted/admin/user/update";
        }
        return "/restricted/user/user/update";
    }
    
    public String entityDelete(TUser entity)
    {
        return super.entityDelele(entity);
    }
    
    public String update(UserLogin currentUser)
    {
        this.setEntity(this.tUserFacade.update(this.getEntity()));
        // Si on est en mode utilisateur
        if(currentUser!=null)
        {
            currentUser.setUser(this.getEntity());
            return "view";
        }
        // Si on est en mode administrateur
        return "list";
    }
    
    public String getAdmin_Rights()
    {
        return Utils.ADMIN_RIGHTS;
    }
    
    public String getUser_Rights()
    {
        return Utils.USER_RIGHTS;
    }
    
    public String getUnknown_Rights()
    {
        return Utils.UNKNOWN_RIGHTS;
    }
    
    @Override
    public String getDeleteMessage(TUser entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cet utilisateur ("+entity.getFirstname()+" "+entity.getName()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }
    
    @Override
    public List<TUser> getEntries()
    {
        return super.findAll();
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.tUserFacade);
    }

    @Override
    public TUser getEntity()
    {
        return super.getInstance();
    }

    @Override
    public void setEntity(TUser entity)
    {
        super.setInstance(entity);
    }
}
