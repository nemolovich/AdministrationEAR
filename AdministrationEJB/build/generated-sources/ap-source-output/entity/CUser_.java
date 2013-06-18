package entity;

import entity.Client;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-18T11:35:38")
@StaticMetamodel(CUser.class)
public class CUser_ { 

    public static volatile SingularAttribute<CUser, Integer> id;
    public static volatile SingularAttribute<CUser, String> phone;
    public static volatile SingularAttribute<CUser, String> name;
    public static volatile SingularAttribute<CUser, Client> idClient;
    public static volatile ListAttribute<CUser, Client> clientList;
    public static volatile SingularAttribute<CUser, Boolean> sleeping;
    public static volatile SingularAttribute<CUser, String> observations;

}