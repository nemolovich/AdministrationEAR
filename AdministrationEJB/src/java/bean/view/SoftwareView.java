/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.SoftwareFacade;
import bean.view.struct.EmbdedDataListView;
import entity.Client;
import entity.Software;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "softwareView")
@SessionScoped
public class SoftwareView extends EmbdedDataListView<Client, Software, SoftwareFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private SoftwareFacade softwareFacade;
    
    public SoftwareView() throws NoSuchMethodException
    {
        super(Software.class,"software",
                Software.class.getMethod("setIdClient", 
                                         new Class<?>[]{Client.class}));
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.softwareFacade);
    }

    @Override
    public List<Software> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Software getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Software entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " ce logiciel ("+entity.toString()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<Software> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous les "
                + "logiciels sélectionnés (";
            boolean first=true;
            for(Software software:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=software.getName()+" id="+software.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }

    @Override
    public void setEntity(Software software)
    {
        super.setInstance(software);
    }
}