/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.TUserFacade;
import entity.TUser;
import entity.User;
import java.io.Serializable;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent; 
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

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
    private int loginTry=0;
    private long nextTryTime;
    private boolean blocked=false;
    /**
     * Code konami: UP UP DOWN DOWN LEFT RIGHT LEFT RIGHT B A
     */
    private static final String konamiCode="uuddlrlrba";
    private String konami="";
    private long previousHit=Calendar.getInstance().getTimeInMillis();
    /**
     * Stateful infos pour les menus
     */
    private static final String menuLeftID="menu";
    private static final String menuRightID="menu_right";
    private int menuLeftSize=200;
    private int menuRightSize=300;
    private boolean menuLeftClosed=false;
    private boolean menuRightClosed=true;
    
    public UserLogin()
    {
    }
    
    public void handleToggle(ToggleEvent event)
    {
        if(event.getComponent().getAttributes().get("id").equals(UserLogin.menuLeftID))
        {
            this.menuLeftClosed=event.getVisibility().equals(Visibility.HIDDEN);
        }
        else if(event.getComponent().getAttributes().get("id").equals(UserLogin.menuRightID))
        {
            this.menuRightClosed=event.getVisibility().equals(Visibility.HIDDEN);
        }
    }
    
    public void handleResize(ResizeEvent event)
    {
        try
        {
            if(event.getComponent().getAttributes().get("id").equals(UserLogin.menuLeftID))
            {
                this.menuLeftSize=Integer.valueOf((String)event.getComponent().getAttributes().get("size"))+5;
            }
            else if(event.getComponent().getAttributes().get("id").equals(UserLogin.menuRightID))
            {
                this.menuRightSize=Integer.valueOf((String)event.getComponent().getAttributes().get("size"))+5;
            }
        }
        catch(NumberFormatException nfe)
        {
            // Si la taille est à "auto"
        }
    }

    public int getMenuLeftSize()
    {
        return menuLeftSize;
    }

    public void setMenuLeftSize(int menuLeftSize)
    {
        this.menuLeftSize = menuLeftSize;
    }

    public int getMenuRightSize()
    {
        return menuRightSize;
    }

    public void setMenuRightSize(int menuRightSize)
    {
        this.menuRightSize = menuRightSize;
    }

    public boolean isMenuLeftClosed()
    {
        return this.menuLeftClosed;
    }

    public void setMenuLeftClosed(boolean menuLeftClosed)
    {
        this.menuLeftClosed = menuLeftClosed;
    }

    public boolean isMenuRightClosed()
    {
        return this.menuRightClosed;
    }

    public void setMenuRightClosed(boolean menuRightClosed)
    {
        this.menuRightClosed = menuRightClosed;
    }
    
    public String getKonami()
    {
        return this.konami;
    }
    
    public void addKonami(char c)
    {
        long currentTime=Calendar.getInstance().getTimeInMillis();
        if(!this.konami.isEmpty()&&currentTime-this.previousHit>500)
        {
            this.konami="";
        }
        this.previousHit=currentTime;
        this.konami+=c;
        if(!UserLogin.konamiCode.startsWith(this.konami))
        {
            this.konami="";
        }
        else if(UserLogin.konamiCode.equals(this.konami))
        {
            FacesContext context=FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage message=new FacesMessage("Code Konami");
            if(this.blocked)
            {
                this.unLock();
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                message.setDetail("Votre session a été débloquée");
            }
            else
            {
                if(this.getLogged())
                {
                    this.logout();
                }
                this.sessionBlockFor(60);
                message.setSeverity(FacesMessage.SEVERITY_FATAL);
                message.setDetail("Vous avez bloqué votre session pour une heure!!!");
            }
            context.addMessage(null, message);
            this.konami="";
        }
    }
    
    public void setLoginTry(int tryNumber)
    {
        this.loginTry = tryNumber;
    }
    
    public int getLoginTry()
    {
        return this.loginTry;
    }
    
    public void sessionBlockFor(int minutes)
    {
        long currentTime=Calendar.getInstance().getTimeInMillis();
        this.nextTryTime=currentTime+60000*minutes;
        this.blocked=true;
    }
    
    public int getWaitTime()
    {
        long currentTime=Calendar.getInstance().getTimeInMillis();
        long remainingTime=this.nextTryTime-currentTime;
        if(remainingTime>0)
        {
            return (int)remainingTime/60000;
        }
        else
        {
            this.blocked=false;
            return -1;
        }
    }
    
    public boolean isBlocked()
    {
        return this.blocked;
    }
    
    public void unLock()
    {
        this.blocked=false;
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
                "/restricted/login")));
    }
    
    public String saveAccount()
    {
        this.tUserFacade.persist(this.user);
        return null;
    }
    
    public boolean getLogged()
    {
        return (this.user!=null&&this.user.getId()!=null&&this.user.getName()!=null);
    }
    
    public String logout()
    {
        this.user=null;
        this.template="unknown";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesMessage message=new FacesMessage("Déconnecté",
                "Vous avez été déconnecté de votre session");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null,message);
                
        return "/restricted/login";
    }
}