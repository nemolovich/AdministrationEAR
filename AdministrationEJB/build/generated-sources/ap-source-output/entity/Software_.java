package entity;

import entity.Client;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-27T12:08:56")
@StaticMetamodel(Software.class)
public class Software_ { 

    public static volatile SingularAttribute<Software, Integer> id;
    public static volatile SingularAttribute<Software, String> editor;
    public static volatile SingularAttribute<Software, Integer> stationNumber;
    public static volatile SingularAttribute<Software, String> name;
    public static volatile SingularAttribute<Software, Client> idClient;
    public static volatile SingularAttribute<Software, Boolean> sleeping;
    public static volatile SingularAttribute<Software, String> license;
    public static volatile SingularAttribute<Software, String> version;
    public static volatile SingularAttribute<Software, String> observations;

}