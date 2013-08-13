/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.SoftwareFacade;
import bean.view.struct.EntityView;
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
public class SoftwareView extends EntityView<Software, SoftwareFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private SoftwareFacade softwareFacade;
    
    public SoftwareView()
    {
        super(Software.class,"software");
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
                + " ce logiciel ("+entity.toString();
//                + " id="+entity.getId()+"). Cette action est irreversible,"
//                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Software software)
    {
        super.setInstance(software);
    }
}