/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.FactureFacade;
import bean.view.struct.EntityView;
import controller.PDFCreatorController;
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
    private PDFCreatorController pdfCreatorController;
    @Inject
    private InterventionView interventionView;
    
    public FactureView()
    {
        super(Facture.class,"facture");
    }
    
    public String create(List<Intervention> list)
    {
        if(list!=null&&this.getInstance()!=null)
        {
            this.getInstance().setInterventionList(list);
            for(Intervention i:list)
            {
                i.setIdFacture(this.getInstance());
                this.interventionView.setInstance(i);
                this.interventionView.update(i.getIdTask());
            }
        }
        this.pdfCreatorController.createPDF(this.getInstance().getFactureNumber(),list);
        return super.create();
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