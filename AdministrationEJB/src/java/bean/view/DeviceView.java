/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.DeviceFacade;
import bean.view.struct.EmbdedDataListView;
import entity.Client;
import entity.Device;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "deviceView")
@SessionScoped
public class DeviceView extends EmbdedDataListView<Client, Device, DeviceFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private DeviceFacade deviceFacade;
    
    public DeviceView() throws NoSuchMethodException
    {
        super(Device.class,"device",
                Device.class.getMethod("setIdClient",
                                            new Class<?>[]{Client.class}));
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.deviceFacade);
    }

    @Override
    public List<Device> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Device getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Device entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " ce poste de travail ("+entity.toString()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<Device> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous les "
                + "postes de travails sélectionnés (";
            boolean first=true;
            for(Device device:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=device.getBrand()+" id="+device.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }

    @Override
    public void setEntity(Device device)
    {
        super.setInstance(device);
    }
}