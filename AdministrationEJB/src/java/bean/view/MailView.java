/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.MailFacade;
import bean.view.struct.EmbdedDataListView;
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
public class MailView extends EmbdedDataListView<Client, Mail, MailFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private MailFacade mailFacade;
    
    public MailView() throws NoSuchMethodException
    {
        super(Mail.class,"mail",
                Mail.class.getMethod("setIdClient",
                                        new Class<?>[]{Client.class}));
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
    public String deleteMessages(List<Mail> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement toutes"
                + " les adresse mail sélectionnées (";
            boolean first=true;
            for(Mail mail:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=mail.getMail()+" id="+mail.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }

    @Override
    public void setEntity(Mail mail)
    {
        super.setInstance(mail);
    }
}