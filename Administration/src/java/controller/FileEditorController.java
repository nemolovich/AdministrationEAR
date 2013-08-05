/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Brian GOHIER
 */
@SessionScoped
@ManagedBean(name="fileEditorController")
public class FileEditorController
{
    private static final String DEFAULT_TEXT="<br/>";
    private String value=DEFAULT_TEXT;

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    
    public void clear()
    {
        this.value = DEFAULT_TEXT;
    }
    
}
