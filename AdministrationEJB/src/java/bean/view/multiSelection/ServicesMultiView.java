/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection;

import bean.view.multiSelection.struct.EntityMultiView;
import entity.Services;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "servicesMultiView")
@SessionScoped
public class ServicesMultiView extends EntityMultiView<Services>
{
    private static final long serialVersionUID = 1L;
    
    public ServicesMultiView()
    {
        super(Services.class);
    }
}
