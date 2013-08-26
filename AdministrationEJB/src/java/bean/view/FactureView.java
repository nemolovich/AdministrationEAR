/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.ApplicationLogger;
import bean.Utils;
import bean.facade.FactureFacade;
import bean.view.struct.EntityView;
import entity.Facture;
import entity.Intervention;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "factureView")
@SessionScoped
public class FactureView extends EntityView<Facture, FactureFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private FactureFacade factureFacade;
    @Inject
    private InterventionView interventionView;
    
    public FactureView()
    {
        super(Facture.class,"facture");
    }
    
    public String create(List<Intervention> list)
    {
        String result=super.create();
        Facture entity=super.getInstance();
        if(list!=null&&entity!=null)
        {
            for(Intervention instance:list)
            {
                List<Intervention> old=entity.getInterventionList();
                instance.setIdFacture(entity);
                this.interventionView.setInstance(instance);
                this.interventionView.updateSilent(instance.getIdTask(),true);
                old.add(instance);
                entity.setInterventionList(old);
                this.update();
                String entity_details=instance.getFullString();
                entity_details=entity_details!=null?entity_details:instance.toString();
                String instance_details=Utils.getFullString(instance);
                instance_details=instance_details!=null?instance_details:instance.toString();
                ApplicationLogger.writeWarning("Modification de l'entité de la classe \""+
                        Intervention.class.getName()+"\" réussie");
                ApplicationLogger.write("\tObjet: \""+Intervention.class.getName()+"\": \""+
                        instance_details+"\"");
                ApplicationLogger.write("\t[INSIDE] Dans la liste de l'objet:\n"+
                        "\tObjet: \""+Facture.class.getName()+"\": \""+
                        entity_details+"\"");
                ApplicationLogger.addSmallSep();
            }
//            instance.setInterventionList(list);
//            Task task=null;
//            for(Intervention i:list)
//            {
//                if(task==null)
//                {
//                    task=i.getIdTask();
//                }
//                i.setIdFacture(instance);
//                this.interventionView.setInstance(i);
//                this.interventionView.updateSilent(task, true);
//            }
        }
        return result;
    }
    
    public String entityPrint(Facture entity)
    {
        super.entityView(entity);
        super.setCreating(true);
        return super.getWebFolder()+"print";
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.factureFacade);
    }

    @Override
    public List<Facture> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Facture getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Facture entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cette facture (n°"+entity.getFactureNumber()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Facture entity)
    {
        super.setInstance(entity);
    }
}