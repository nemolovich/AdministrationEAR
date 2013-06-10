package entity;

import entity.CUser;
import entity.Mail;
import entity.Software;
import entity.Workstation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-10T18:13:41")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile ListAttribute<Client, Mail> mailList;
    public static volatile SingularAttribute<Client, Integer> postalcode;
    public static volatile SingularAttribute<Client, String> mail;
    public static volatile SingularAttribute<Client, String> interventionType;
    public static volatile SingularAttribute<Client, String> phone;
    public static volatile SingularAttribute<Client, String> fax;
    public static volatile ListAttribute<Client, Workstation> workstationList;
    public static volatile SingularAttribute<Client, String> operator;
    public static volatile SingularAttribute<Client, String> observations;
    public static volatile ListAttribute<Client, Software> softwareList;
    public static volatile SingularAttribute<Client, Integer> id;
    public static volatile SingularAttribute<Client, String> address;
    public static volatile ListAttribute<Client, CUser> cUserList;
    public static volatile SingularAttribute<Client, String> name;
    public static volatile SingularAttribute<Client, CUser> idUser;
    public static volatile SingularAttribute<Client, Double> deplacement;
    public static volatile SingularAttribute<Client, String> internetPassword;
    public static volatile SingularAttribute<Client, String> internetLogin;
    public static volatile SingularAttribute<Client, Double> tarif;

}