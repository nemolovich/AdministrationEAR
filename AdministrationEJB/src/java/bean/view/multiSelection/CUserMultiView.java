/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection;

import bean.view.multiSelection.struct.EntityMultiView;
import entity.CUser;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "cUserMultiView")
@SessionScoped
public class CUserMultiView extends EntityMultiView<CUser>
{
    private static final long serialVersionUID = 1L;
    
    public CUserMultiView()
    {
        super(CUser.class);
    }
}
