/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entity.Intervention;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "factureTotal")
@SessionScoped
public class FactureTotal implements Serializable
{
    private static final long serialVersionUID = 1L;
    private double totalDuration=0;
    private double totalTarif=0;
    private int totalDeplacements=0;
    private double interventionTarif=0;
    private double deplacementTarif=0;

    public double getTotalDuration()
    {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration)
    {
        this.totalDuration = totalDuration;
    }

    public double getTotalTarif()
    {
        return totalTarif;
    }

    public void setTotalTarif(double totalTarif)
    {
        this.totalTarif = totalTarif;
    }

    public int getTotalDeplacements()
    {
        return totalDeplacements;
    }

    public void setTotalDeplacements(int totalDeplacements)
    {
        this.totalDeplacements = totalDeplacements;
    }

    public double getInterventionTarif()
    {
        return interventionTarif;
    }

    public void setInterventionTarif(double interventionTarif)
    {
        this.interventionTarif = interventionTarif;
    }

    public double getDeplacementTarif()
    {
        return deplacementTarif;
    }

    public void setDeplacementTarif(double deplacementTarif)
    {
        this.deplacementTarif = deplacementTarif;
    }
    
    public void totalCalculate(List<Intervention> list)
    {
        this.deplacementTarif=0;
        this.interventionTarif=0;
        this.totalDeplacements=0;
        this.totalDuration=0;
        this.totalTarif=0;
        if(list!=null&&!list.isEmpty())
        {
            for(Intervention entity:list)
            {
                if(this.deplacementTarif==0||
                        this.interventionTarif==0)
                {
                    this.interventionTarif=entity.getIdTask().getIdClient().getTarifValue();
                    this.deplacementTarif=entity.getIdTask().getIdClient().getDeplacementValue();
                }
                this.totalDeplacements+=entity.getDeplacement()?1:0;
                this.totalDuration+=entity.getDuration();
                String tarifS=String.format("%.02f",
                        entity.getDuration()*this.interventionTarif);
                double tarif=Double.valueOf(tarifS.replace(',', '.'));
                this.totalTarif+=tarif;
            }
        }
    }
    
}
