/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.data;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value="saveSchedule")
@Singleton
@Startup
public class SaveSchedule
{
    @Resource
    private TimerService service;
    private long start;
    
    public SaveSchedule()
    {
        System.out.println("Le timer pour la sauvegarde est lancé");
    }
    
    @PostConstruct
    public void init()
    {
        this.start=(new Date()).getTime();
        ScheduleExpression exp=new ScheduleExpression();
        exp.dayOfMonth("*").hour("20").minute("0").second("0");
        service.createCalendarTimer(exp);
    }
    
    @Timeout
    public void timeOut()
    {
        long act=(new Date()).getTime();
        System.out.println("Sauvegarde automatique de la base de données: "+((act-this.start)/1000)+" secondes");
        this.start=act;
        DataManager.saveData();
    }
    
}
