package entity;

import entity.CUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-04T18:05:44")
@StaticMetamodel(Workstation.class)
public class Workstation_ { 

    public static volatile SingularAttribute<Workstation, Integer> id;
    public static volatile SingularAttribute<Workstation, Date> startDate;
    public static volatile SingularAttribute<Workstation, String> operatingSystem;
    public static volatile SingularAttribute<Workstation, String> monitor;
    public static volatile SingularAttribute<Workstation, String> ram;
    public static volatile SingularAttribute<Workstation, String> hardDrive;
    public static volatile SingularAttribute<Workstation, String> wsType;
    public static volatile SingularAttribute<Workstation, String> videoCard;
    public static volatile SingularAttribute<Workstation, CUser> idClient;
    public static volatile SingularAttribute<Workstation, String> brand;
    public static volatile SingularAttribute<Workstation, String> processor;
    public static volatile SingularAttribute<Workstation, String> observations;

}