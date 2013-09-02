/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Files;
import bean.Utils;
import bean.log.ApplicationLogger;
import bean.view.FactureView;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import controller.utils.PDFDocument;
import controller.utils.PDFTable;
import entity.CUser;
import entity.Client;
import entity.Device;
import entity.Intervention;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Brian GOHIER
 */
@SessionScoped
@ManagedBean(name="PDFCreatorController")
public class PDFCreatorController implements Serializable
{
    private static final long serialVersionUID = 1L;
//    private static int test=0;
//    private static int testi=100;
	
    public synchronized String createDevicePDF(Client client, List<Device> list)
    {
//        List<Device> temp=new ArrayList<Device>(list);
//        Collections.sort(temp, Collections.reverseOrder());
//        list.clear();
//        for(int i=0;i<test;i++)
//        {
//            list.add(temp.get(i%temp.size()));
//        }
//        if(client==null||list==null)
//        {
//                return "#";
//        }
        String clientId=String.format("%08d", client.getId());
//        clientId=String.format("%08d", test);
        String clientName=client.getName();
        clientName=clientName!=null&&clientName.length()>=25?clientName.substring(0,22)+"...":clientName;
        PDFDocument pdf=new PDFDocument(PageSize.A4.rotate());
        File pdfFile=new File(Utils.getResourcesPath()+"generated"+File.separator+
                "devices"+File.separator+"Devices_Client_"+clientId+".pdf");
        if(!pdfFile.exists())
        {
            Files.createIfNotExists(pdfFile, false);
        }
        Exception opened=null;
        if(pdfFile.canWrite())
        {
            try
            {
                PdfWriter.getInstance(pdf, new FileOutputStream(pdfFile));
                opened=null;
            }
            catch (DocumentException ex)
            {
//                ex.printStackTrace();
                opened=ex;
            }
            catch (FileNotFoundException ex)
            {
//                ex.printStackTrace();
                opened=ex;
            }
        }
        if(opened!=null||pdf.isOpen())
        {
            ApplicationLogger.writeError("Le fichier \""+pdfFile.getAbsolutePath()+
                    "\" est déjà ouvert par une autre instance", opened);
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Le fichier que vous tentez de créé est déjà ouvert par une autre instance, "
                    + "veuillez le fermer avant de continuer");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        Exception written;
        try
        {
            pdf.open();
            pdf.setFooter("Périphériques pour "+clientName, "ADMIN Services", Element.ALIGN_RIGHT);
            
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String logo = servletContext.getRealPath("")+File.separator+
                    "resources"+File.separator+"img"+File.separator+"logoAS.png";
            Image logoImg = Image.getInstance(logo);
            logoImg.scaleAbsolute(100, 57);
            
            PDFTable header = new PDFTable(2);
            header.setCellVerticalAlignment(Element.ALIGN_TOP);
            header.setSpacingAfter(30);
            
            logoImg.setAlignment(Element.ALIGN_LEFT);
            header.addSize(logoImg, Element.ALIGN_LEFT, 1, 0, 0);
            
            Paragraph rightTop=new Paragraph("Périphériques de la société "+clientName);
            header.addSize(rightTop, Element.ALIGN_RIGHT, 1, 0, 0);
            
            Paragraph leftBottom=new Paragraph();
            leftBottom.add(new Paragraph("8 Rue Louise WEISS\n"
                    + "37700 La Ville aux Dames"));
            Font littleFont=FontFactory.getFont(FontFactory.HELVETICA, 8.5f, Font.NORMAL);
            Paragraph tel=new Paragraph("                            Tél: 02.47.44.71.14",littleFont);
            leftBottom.add(tel);
            header.addSize(leftBottom, Element.ALIGN_LEFT, 1, 0, 0);
            header.addSize(new Phrase(), Element.ALIGN_RIGHT, 1, 0, 0);
            
            boolean none=list.isEmpty();
            
            pdf.add(header);
            PDFTable table;
            Paragraph details = new Paragraph();
            int nbLinesOnPage=0;
            int multiLines=0;
            int nbLines=0;
            int breakedPages=0;
            int firstPageMaxLines=19;
            int othersPagesMaxLines=28;
            float pageSize;
            if(none)
            {
                table=new PDFTable(1);
                table.add(new Paragraph("Aucun périphérique"));
            }
            else
            {
                PDFTable tab=new PDFTable(3);
                tab.setCellVerticalAlignment(Element.ALIGN_TOP);
                final float tabSizes[]={50,25,25};
                tab.setWidths(tabSizes);
                tab.add(new Phrase("Liste des périphériques de la société"), Element.ALIGN_LEFT);
                tab.add(new Phrase(), Element.ALIGN_CENTER);
                tab.add(new Phrase("Total périphériques: "+list.size()), Element.ALIGN_CENTER);
                
                details.add(tab);
                details.setSpacingBefore(0);
                details.setSpacingAfter(20);
                pdf.add(details);
                Collections.sort(list, Collections.reverseOrder());
                table=new PDFTable(7);
                final String[][] tableHeader={
                    {"Date",                    String.valueOf(Element.ALIGN_CENTER)},
                    {"Type",                    String.valueOf(Element.ALIGN_LEFT)},
                    {"Marque",                  String.valueOf(Element.ALIGN_LEFT)},
                    {"Nom",                     String.valueOf(Element.ALIGN_LEFT)},
                    {"Processeur",              String.valueOf(Element.ALIGN_LEFT)},
                    {"Système d'exploitation",  String.valueOf(Element.ALIGN_LEFT)},
                    {"Utilisateur",  String.valueOf(Element.ALIGN_LEFT)}};
                final float tableSizes[]={9.2f,12.4f,14,17.4f,18,20,10};
                table.setHeader(tableHeader);
                table.setWidths(tableSizes);
                table.setCellVerticalAlignment(Element.ALIGN_TOP);
                Font cellFont=FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
                BaseFont bf=cellFont.getBaseFont();
                int nbLinesMax=firstPageMaxLines;
                /**
                 * 30 = header.setSpacingAfter(30);
                 * 20 = details.setSpacingAfter(20);
                 * 3 = Espace inconnu
                 */
                pageSize=header.getTotalHeight()+details.getTotalLeading()
                        +30+20+3;
                for(Device d:list)
                {
                    Color color=null;
                    if((nbLines-multiLines)%2!=0)
                    {
                        color=Color.decode("#F2F5F9");
                    }
                    int lineSize=1;
                    if(lineSize>1)
                    {
                        multiLines+=lineSize-1;
                    }
                    table.addBordered(new Paragraph(Utils.smallDateFormat(
                            d.getStartDate()), cellFont),
                            Element.ALIGN_CENTER,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String type=d.getWsType();
                    type=(type==null)?"":type;
                    //82.82
                    if(bf.getWidthPointKerned(type, 10)>82.82)
                    {
                        while(bf.getWidthPointKerned(type, 10)>82.82)
                        {
                            type=type.substring(0,type.length()-1);
                        }
                        type+=".";
                    }
                    table.addBordered(new Paragraph(type, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String brand=d.getBrand();
                    brand=(brand==null)?"":brand;
                    //96.16
                    if(bf.getWidthPointKerned(brand, 10)>96.16)
                    {
                        while(bf.getWidthPointKerned(brand, 10)>96.16)
                        {
                            brand=brand.substring(0,brand.length()-1);
                        }
                        brand+=".";
                    }
                    table.addBordered(new Paragraph(brand, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String name=d.getName();
                    name=(name==null)?"":name;
                    //122.84
                    if(bf.getWidthPointKerned(name, 10)>122.84)
                    {
                        while(bf.getWidthPointKerned(name, 10)>122.84)
                        {
                            name=name.substring(0,name.length()-1);
                        }
                        name+=".";
                    }
                    table.addBordered(new Paragraph(name, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String proc=d.getProcessor();
                    proc=(proc==null)?"":proc;
                    //122.84
                    if(bf.getWidthPointKerned(proc, 10)>122.84)
                    {
                        while(bf.getWidthPointKerned(proc, 10)>122.84)
                        {
                            proc=proc.substring(0,proc.length()-1);
                        }
                        proc+=".";
                    }
                    table.addBordered(new Paragraph(proc, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String os=d.getOperatingSystem();
                    os=(os==null)?"":os;
                    //142.85
                    if(bf.getWidthPointKerned(os, 10)>142.85)
                    {
                        while(bf.getWidthPointKerned(os, 10)>142.85)
                        {
                            os=os.substring(0,os.length()-1);
                        }
                        os+=".";
                    }
                    table.addBordered(new Paragraph(os, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String user=d.getUserNameDefault();
                    user=(user==null)?"":user;
                    //62.81
                    if(bf.getWidthPointKerned(user, 10)>62.81)
                    {
                        while(bf.getWidthPointKerned(user, 10)>62.81)
                        {
                            user=user.substring(0,user.length()-1);
                        }
                        user+=".";
                    }
                    table.addBordered(new Paragraph(user, cellFont),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    nbLines+=lineSize;
                    nbLinesOnPage+=lineSize;
                    if(nbLinesOnPage>=nbLinesMax&&
                            nbLines-multiLines<list.size())
                    {
                        pdf.add(table);
                        pdf.newPage(pageSize+table.getTotalHeight());
                        table=new PDFTable(7);
                        table.setHeader(tableHeader);
                        table.setWidths(tableSizes);
                        table.setCellVerticalAlignment(Element.ALIGN_TOP);
                        pageSize=0;
                        nbLinesOnPage=0;
                        nbLinesMax=othersPagesMaxLines;
                    }
                }
            }
            
            pdf.add(table);
            
            if(nbLines>firstPageMaxLines||breakedPages>0)
            {
                pageSize=table.getTotalHeight();
            }
            else
            {
                pageSize=header.getTotalHeight()+table.getTotalHeight()+
                        details.getTotalLeading()+10+4;
                if(!none)
                {
                    pageSize+=30+6+3;
                }
            }
            pdf.addFooter(pageSize);
            
            pdf.close();
            written=null;
        }
        catch (MalformedURLException ex)
        {
            ex.printStackTrace();
            written=ex;
        }
        catch (BadElementException ex)
        {
            ex.printStackTrace();
            written=ex;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            written=ex;
        }
        catch (DocumentException ex)
        {
            ex.printStackTrace();
            written=ex;
        }
        
        if(written!=null||pdf.isOpen())
        {
            ApplicationLogger.writeError("Erreur lors de la création du PDF \""+
                    pdfFile.getAbsolutePath()+"\"", written);
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Erreur lors de l'écriture du PDF");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        try
        {
            File societyFile=null;
            if(client.getIdFilePath()!=null)
            {
                societyFile=new File(Utils.getResourcesPath()+Utils.getUploadsPath()+
                        client.getIdFilePath().getFilePath()+File.separator+
                        "Devices_Client_"+clientId+".pdf");
            }
            if(societyFile!=null)
            {
                Files.copy(pdfFile, societyFile);
            }
        }
        catch (IOException ex)
        {
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non copié",
                    "Le PDF n'a pas pu être copié vers le dossier de cette société");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF Créé",
                "La création du PDF s'est terminée correctement");
        FacesContext.getCurrentInstance().addMessage(null, msg);
//        if(test<testi)
//        {
//            test++;
//            return this.createDevicePDF(client, temp);
//        }
        return "#";
    }
    
    public synchronized String createFacturePDF(FactureView factureView, List<Intervention> list,
            Date startDate, Date endDate)
    {
        factureView.create(list);
        String factureNumber=factureView.getInstance().getFactureNumber();
        PDFDocument pdf=new PDFDocument(PageSize.A4.rotate());
        File pdfFile=new File(Utils.getResourcesPath()+"generated"+File.separator+
                "releves"+File.separator+"Releve_"+factureNumber+".pdf");
        Client client = null;
        if(!pdfFile.exists())
        {
            Files.createIfNotExists(pdfFile, false);
        }
        Exception opened=null;
        if(pdfFile.canWrite())
        {
            try
            {
                PdfWriter.getInstance(pdf, new FileOutputStream(pdfFile));
                opened=null;
            }
            catch (DocumentException ex)
            {
//                ex.printStackTrace();
                opened=ex;
            }
            catch (FileNotFoundException ex)
            {
//                ex.printStackTrace();
                opened=ex;
            }
        }
        if(opened!=null||pdf.isOpen())
        {
            ApplicationLogger.writeError("Le fichier \""+pdfFile.getAbsolutePath()+
                    "\" est déjà ouvert par une autre instance", opened);
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Le fichier que vous tentez de créé est déjà ouvert par une autre instance, "
                    + "veuillez le fermer avant de continuer");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        Exception written;
        try
        {
            pdf.open();
            pdf.setFooter("Détails du relevé de la facture n°"+factureNumber, "ADMIN Services", Element.ALIGN_RIGHT);
            
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String logo = servletContext.getRealPath("")+File.separator+
                    "resources"+File.separator+"img"+File.separator+"logoAS.png";
            Image logoImg = Image.getInstance(logo);
            logoImg.scaleAbsolute(100, 57);
            
            PDFTable header = new PDFTable(2);
            header.setCellVerticalAlignment(Element.ALIGN_TOP);
            header.setSpacingAfter(30);
            
            logoImg.setAlignment(Element.ALIGN_LEFT);
            header.addSize(logoImg, Element.ALIGN_LEFT, 1, 0, 0);
            
            Paragraph rightTop=new Paragraph("Facture n°"+factureNumber);
            header.addSize(rightTop, Element.ALIGN_RIGHT, 1, 0, 0);
            
            Paragraph leftBottom=new Paragraph();
            leftBottom.add(new Paragraph("8 Rue Louise WEISS\n"
                    + "37700 La Ville aux Dames"));
            Font littleFont=FontFactory.getFont(FontFactory.HELVETICA, 8.5f, Font.NORMAL);
            Paragraph tel=new Paragraph("                            Tél: 02.47.44.71.14",littleFont);
            leftBottom.add(tel);
            header.addSize(leftBottom, Element.ALIGN_LEFT, 1, 0, 0);
            
            Intervention item;
            String societyName = null;
            double tarifIntervention = .0f;
            double tarifDeplacement = .0f;
            PDFTable tab1;
            boolean none=false;
            if(list!=null&&!list.isEmpty())
            {
                item=list.get(0);
                client=item.getIdTask().getIdClient();
                societyName=client.getName();
                tarifIntervention=client.getTarifValue();
                tarifDeplacement=client.getDeplacementValue();
                Font fontB = new Font();
                fontB.setStyle(Font.BOLD);
                Font font = new Font();
                Phrase ph1=new Phrase("Tarif de l'intervention:",font);
                Paragraph ph2=new Paragraph(String.format(" %.02f €", tarifIntervention),fontB);
                Phrase ph3=new Phrase("Tarif déplacement:",font);
                Paragraph ph4=new Paragraph(String.format(" %.02f €", tarifDeplacement),fontB);
                tab1=new PDFTable(3,30);
                tab1.setCellVerticalAlignment(Element.ALIGN_TOP);
                final float[] sizes={50,35,15};
                tab1.setWidths(sizes);
                tab1.addSize(new Phrase(),Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph1,Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph2,Element.ALIGN_RIGHT, 1, 0, 0);
                tab1.addSize(new Phrase(),Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph3,Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph4,Element.ALIGN_RIGHT, 1, 0, 0);
                tab1.setSpacingAfter(0);
                tab1.setSpacingBefore(0);
            }
            else
            {
                none=true;
                tab1=new PDFTable(1);
                tab1.add();
            }
            header.addSize(tab1, Element.ALIGN_RIGHT, 1, 0, 0);
            
            pdf.add(header);
            PDFTable table;
            Paragraph details = new Paragraph();
            int nbLinesOnPage=0;
            int multiLines=0;
            int nbLines=0;
            int breakedPages=0;
            int firstPageMaxLines=17;
            int othersPagesMaxLines=25;
            float pageSize;
            if(none)
            {
                table=new PDFTable(1);
                table.add(new Paragraph("Aucune intervention"));
            }
            else
            {
                PDFTable tab=new PDFTable(3);
                tab.setCellVerticalAlignment(Element.ALIGN_TOP);
                final float tabSizes[]={25,50,25};
                tab.setWidths(tabSizes);
                societyName=societyName!=null&&societyName.length()>=25?societyName.substring(0,22)+"...":societyName;
                tab.add(new Phrase(societyName), Element.ALIGN_LEFT);
                tab.add(new Phrase("Relevé de prestations du "+Utils.smallDateFormat(startDate)+
                        " au "+Utils.smallDateFormat(endDate)), Element.ALIGN_CENTER);
                if(client==null)
                {
                    return "#";
                }
                String interventionType=client.getInterventionType();
                interventionType=interventionType.length()>=25?interventionType.substring(0,22)+"...":interventionType;
                tab.add(new Phrase(interventionType), Element.ALIGN_RIGHT);
                
                details.add(tab);
                details.setSpacingBefore(0);
                details.setSpacingAfter(20);
                pdf.add(details);
                Collections.sort(list);
                table=new PDFTable(6);
                final String[][] tableHeader={
                    {"Date",    String.valueOf(Element.ALIGN_CENTER)},
                    {"Interlocuteur",   String.valueOf(Element.ALIGN_CENTER)},
                    {"Object",   String.valueOf(Element.ALIGN_CENTER)},
                    {"Durée",   String.valueOf(Element.ALIGN_CENTER)},
                    {"Dépl.",   String.valueOf(Element.ALIGN_CENTER)},
                    {"Montant",   String.valueOf(Element.ALIGN_CENTER)}};
                final float tableSizes[]={9.2f,12.4f,55,6,4.8f,12.6f};
                table.setHeader(tableHeader);
                table.setWidths(tableSizes);
                table.setCellVerticalAlignment(Element.ALIGN_TOP);
                double totalTarif=.0f;
                double durations=.0f;
                int deplacements=0;
                int nbLinesMax=firstPageMaxLines;
                /**
                 * 30 = header.setSpacingAfter(30);
                 * 20 = details.setSpacingAfter(20);
                 * 3 = Espace inconnu
                 */
                pageSize=header.getTotalHeight()+details.getTotalLeading()
                        +30+20+3;
                for(Intervention i:list)
                {
                    CUser user=i.getIdTask().getIdUser();
                    if(user==null)
                    {
                        user=i.getIdTask().getIdClient().getIdUser();
                    }
                    Color color=null;
                    if((nbLines-multiLines)%2!=0)
                    {
                        color=Color.decode("#F2F5F9");
                    }
                    int lineSize=(1+i.getIdTask().getDescription().length()-
                            i.getIdTask().getDescription().replaceAll("\\n", "").length());
                    boolean breaked=false;
                    if(nbLinesOnPage+1>=nbLinesMax&&lineSize>1||
                            (list!=null&&nbLines+1-multiLines>=list.size()&&
                            nbLinesOnPage+3>=nbLinesMax))
                    {
                        pdf.add(table);
                        pdf.newPage(pageSize+table.getTotalHeight());
                        table=new PDFTable(6);
                        table.setHeader(tableHeader);
                        table.setWidths(tableSizes);
                        table.setCellVerticalAlignment(Element.ALIGN_TOP);
                        pageSize=0;
                        nbLinesMax=othersPagesMaxLines;
                        nbLinesOnPage=0;
                        breakedPages++;
                        breaked=true;
                    }
                    if(lineSize>1)
                    {
                        multiLines+=lineSize-1;
                    }
                    table.addBordered(new Paragraph(Utils.smallDateFormat(
                            i.getInterventionDate())),
                            Element.ALIGN_CENTER,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String userName=user==null?"":user.getName();
                    if(userName.length()>9)
                    {
                        userName=userName.substring(0,9);
                    }
                    table.addBordered(new Paragraph(userName),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    table.addBordered(new Paragraph(i.getIdTask().getDescription()),
                            Element.ALIGN_LEFT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    double duration=i.getDuration();
                    durations+=duration;
                    table.addBordered(new Paragraph(Utils.getDurationString(
                            Utils.getTimeFormat(duration))),
                            Element.ALIGN_RIGHT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    deplacements+=i.getDeplacement()?1:0;
                    table.addBordered(new Paragraph(i.getDeplacement()?"Oui":"Non"),
                            Element.ALIGN_RIGHT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    String tarif=String.format("%.02f",tarifIntervention*i.getDuration());
                    totalTarif+=Double.valueOf("0"+tarif.replace(',', '.'));
                    table.addBordered(new Paragraph(tarif+" €"),
                            Element.ALIGN_RIGHT,
                            PDFTable.BORDER_BOTTOM,
                            color);
                    nbLines+=lineSize;
                    nbLinesOnPage+=lineSize;
                    if(nbLinesOnPage>=nbLinesMax&&!breaked)
                    {
                        pdf.add(table);
                        pdf.newPage(pageSize+table.getTotalHeight());
                        table=new PDFTable(6);
                        table.setHeader(tableHeader);
                        table.setWidths(tableSizes);
                        table.setCellVerticalAlignment(Element.ALIGN_TOP);
                        pageSize=0;
                        nbLinesOnPage=0;
                        nbLinesMax=othersPagesMaxLines;
                    }
                }
                double totalDeplacements=deplacements*tarifDeplacement;
                String totalDuration=Utils.getDurationString(Utils.getTimeFormat(durations));
                final String[][] footerListTop={
                    {"Interventions:",String.valueOf(Element.ALIGN_RIGHT)},
                    {totalDuration,String.valueOf(Element.ALIGN_RIGHT)},
                    {"",String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%.02f €", totalTarif),String.valueOf(Element.ALIGN_RIGHT)},
                };
                final String[][] footerListMiddle={
                    {"Déplacements:",String.valueOf(Element.ALIGN_RIGHT)},
                    {"",String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%d", deplacements),String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%.02f €", totalDeplacements),String.valueOf(Element.ALIGN_RIGHT)},
                };
                final String[][] footerListBottom={
                    {"Total:",String.valueOf(Element.ALIGN_RIGHT)},
                    {"",String.valueOf(Element.ALIGN_RIGHT)},
                    {"",String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%.02f €", totalDeplacements+totalTarif),String.valueOf(Element.ALIGN_RIGHT)},
                };
                table.setFooter(footerListTop, PDFTable.BORDER_TOP);
                table.setFooter(footerListMiddle, PDFTable.BORDER_NO);
                table.setFooter(footerListBottom, PDFTable.BORDER_BOTTOM);
            }
            
            pdf.add(table);
            
            if(nbLines+1>=firstPageMaxLines||breakedPages>0)
            {
                pageSize=table.getTotalHeight();
            }
            else
            {
                pageSize=header.getTotalHeight()+table.getTotalHeight()+
                        details.getTotalLeading()+10+4;
                if(!none)
                {
                    pageSize+=30+6+3;
                }
            }
            pdf.addFooter(pageSize);
            
            pdf.close();
            written=null;
        }
        catch (MalformedURLException ex)
        {
//            ex.printStackTrace();
            written=ex;
        }
        catch (BadElementException ex)
        {
//            ex.printStackTrace();
            written=ex;
        }
        catch (IOException ex)
        {
//            ex.printStackTrace();
            written=ex;
        }
        catch (DocumentException ex)
        {
//            ex.printStackTrace();
            written=ex;
        }
        
        if(written!=null||pdf.isOpen())
        {
            ApplicationLogger.writeError("Erreur lors de la création du PDF \""+
                    pdfFile.getAbsolutePath()+"\"", written);
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Erreur lors de l'écriture du PDF");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        try
        {
            if(client==null)
            {
                return "#";
            }
            File societyFile=null;
            if(client.getIdFilePath()!=null)
            {
                societyFile=new File(Utils.getResourcesPath()+Utils.getUploadsPath()+
                        client.getIdFilePath().getFilePath()+File.separator+
                        "Releve_"+factureNumber+".pdf");
            }
            if(societyFile!=null)
            {
                Files.copy(pdfFile, societyFile);
            }
        }
        catch (IOException ex)
        {
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non copié",
                    "Le PDF n'a pas pu être copié vers le dossier de cette société");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF Créé",
                "La création du PDF s'est terminée correctement");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "#";
    }
}
