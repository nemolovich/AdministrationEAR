/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.TUserFacade;
import entity.TUser;
import entity.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Brian GOHIER
 */
@Named("userLogin")
@Stateless
public class UserLogin implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private User user;
    @EJB
    private TUserFacade tUserFacade;
    private String loginMail="";
    private String template="unknown";
    
    public UserLogin()
    {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session;
//        if(context!=null)
//        {
//            session = (HttpSession) context.getExternalContext().getSession(false);
//        }
//        else
//        {
//            return;
//        }
//        String sessionId;
//        if(session==null)
//        {
//            System.out.println("Create session");
//            session=(HttpSession) context.getExternalContext().getSession(true);
//        }
//        if(session==null)
//        {
//            System.err.println("NO SESSION");
//        }
//        else
//        {
//            sessionId=session.getId();
//            System.out.println("Session ID: "+sessionId);
//        }
    }
    
    public void setTemplate(String template)
    {
        this.template = template;
    }
    
    public String getTemplate()
    {
        return this.template;
    }
    
    public void setLoginMail(String loginMail)
    {
        this.loginMail = loginMail;
    }
    
    public String getLoginMail()
    {
        return this.loginMail==null?"":this.loginMail;
    }
    
    public Integer getId()
    {
        return this.user==null?-1:this.user.getId();
    }

    public void setId(Integer id)
    {
        this.user.setId(id);
    }

    public String getMail()
    {
        return this.user==null?"":this.user.getMail();
    }

    public void setMail(String mail)
    {
        this.user.setMail(mail);
    }

    public String getName()
    {
        return this.user==null?"":this.user.getName();
    }

    public void setName(String name)
    {
        this.user.setName(name);
    }

    public String getFirstname()
    {
        return this.user==null?"":this.user.getFirstname();
    }

    public void setFirstname(String firstname)
    {
        this.user.setFirstname(firstname);
    }

    public String getPassword()
    {
        return this.user==null?"":this.user.getPassword();
    }
    
    public void setPassword(String password)
    {
        this.user.setPassword(password);
    }
    
    public String getRights()
    {
        return this.user==null?TUser.UNKNOWN_RIGHTS:this.user.getRights();
    }
    
    public void setRights(String rights)
    {
        this.user.setRights(rights);
    }
    
    public void setUser(TUser user)
    {
        this.user=new User(user);
    }
    
    public TUser getUser()
    {
        for(TUser u:this.tUserFacade.findAll())
        {
            if(u.equals(this.user))
            {
                return u;
            }
        }
        System.err.println("Utilisateur non trouvé");
        return null;
    }
    
    public String login()
    {
        this.setTemplate(this.user.getRights().toLowerCase());
        return (this.user==null?null:
                (this.user.getRights().equals(TUser.ADMIN_RIGHTS)?"/restricted/admin/index":
                (this.user.getRights().equals(TUser.USER_RIGHTS)?"/restricted/user/index":
                "/index")));
    }
    
    public String saveAccount()
    {
        this.tUserFacade.persist(this.user);
        return null;
    }
    
    public String getLoggedHeader()
    {
        if(this.getLogged())
        {
            return "Vous êtes connecté en tant que:\n"
                    + this.user.getFirstname()+" "
                    + this.user.getName();
        }
        else
        {
            return "Vous n'êtes pas connecté";
        }
    }
    
    public boolean getLogged()
    {
        return (this.user!=null&&this.user.getId()!=null&&this.user.getName()!=null);
    }
    
    public String logout()
    {
        this.user=null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index";
    }

}