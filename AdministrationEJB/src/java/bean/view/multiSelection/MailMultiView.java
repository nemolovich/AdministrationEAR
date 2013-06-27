/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection;

import bean.view.multiSelection.struct.EntityMultiView;
import entity.Mail;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "mailMultiView")
@SessionScoped
public class MailMultiView extends EntityMultiView<Mail>
{
    private static final long serialVersionUID = 1L;
    
    public MailMultiView()
    {
        super(Mail.class);
    }
}
