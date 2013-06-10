/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bean.facade.MailFacade;
import bean.viewStruct.EntityView;
import entity.Client;
import entity.Mail;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "mailView")
@SessionScoped
public class MailView extends EntityView<Mail, MailFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private MailFacade mailFacade;
    
    public MailView()
    {
        super(Mail.class,"mail");
    }
    
    public String entityCreate(Client client)
    {
        super.entityCreate();
        super.getInstance().setIdClient(client);
        return null;
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.mailFacade);
    }

    @Override
    public List<Mail> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Mail getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Mail entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cette adresse mail ("+entity.getMail()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Mail mail)
    {
        super.setInstance(mail);
    }
}