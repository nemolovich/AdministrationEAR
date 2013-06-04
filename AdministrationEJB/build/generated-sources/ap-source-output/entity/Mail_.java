package entity;

import entity.CUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-04T18:05:44")
@StaticMetamodel(Mail.class)
public class Mail_ { 

    public static volatile SingularAttribute<Mail, Integer> id;
    public static volatile SingularAttribute<Mail, String> popPassword;
    public static volatile SingularAttribute<Mail, String> mail;
    public static volatile SingularAttribute<Mail, String> smtp;
    public static volatile SingularAttribute<Mail, String> smtpPassword;
    public static volatile SingularAttribute<Mail, CUser> idClient;
    public static volatile SingularAttribute<Mail, String> pop;

}