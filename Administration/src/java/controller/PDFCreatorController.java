/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.utilsPDF.PDFDocument;
import bean.Files;
import bean.Utils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import controller.utilsPDF.PDFTable;
import entity.CUser;
import entity.Client;
import entity.Intervention;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
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
    
    public String createPDF(String factureNumber, List<Intervention> list,
            Date startDate, Date endDate)
    {
        PDFDocument pdf=new PDFDocument(PageSize.A4.rotate());
        File pdfFile=new File(Utils.getResourcesPath()+"generated"+File.separator+
                "releves"+File.separator+"Releve_"+factureNumber+".pdf");
        if(!pdfFile.exists())
        {
            Files.createIfNotExists(pdfFile, false);
        }
        boolean opened=!pdfFile.canWrite();
        if(!opened)
        {
            try
            {
                PdfWriter.getInstance(pdf, new FileOutputStream(pdfFile));
                opened=false;
            }
            catch (DocumentException ex)
            {
//                ex.printStackTrace();
                opened=true;
            }
            catch (FileNotFoundException ex)
            {
//                ex.printStackTrace();
                opened=true;
            }
        }
        if(opened||pdf.isOpen())
        {
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Le fichier que vous tentez de créé est déjà ouvert par une autre instance, "
                    + "veuillez le fermer avant de continuer");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        boolean written;
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
            
            Paragraph leftBottom=new Paragraph("8 Rue Louise WEISS\n37700 La Ville aux Dames");
            header.addSize(leftBottom, Element.ALIGN_LEFT, 1, 0, 0);
            
            Intervention item;
            Client client = null;
            String societyName = null;
            PDFTable tab1;
            boolean none=false;
            if(list!=null&&!list.isEmpty())
            {
                item=list.get(0);
                client=item.getIdTask().getIdClient();
                societyName=client.getName();
                Font fontB = new Font();
                fontB.setStyle(Font.BOLD);
                Font font = new Font();
                Phrase ph1=new Phrase(new Chunk("Tarif de l'intervention:",font));
                Paragraph ph2=new Paragraph(String.format(" %.02f €", client.getTarifValue()),fontB);
                Phrase ph3=new Phrase(new Chunk("Tarif déplacement:",font));
                Paragraph ph4=new Paragraph(String.format(" %.02f €", client.getDeplacementValue()),fontB);
                tab1=new PDFTable(2,15);
                final float[] sizes={70,30};
                tab1.setWidths(sizes);
                tab1.setHorizontalAlignment(Element.ALIGN_LEFT);
                tab1.addSize(ph1,Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph2,Element.ALIGN_RIGHT, 1, 0, 0);
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
            int nbLine=1;
            int firstPageMaxLine=17;
            int othersPagesMaxLine=25;
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
                tab.add(new Phrase(societyName), Element.ALIGN_LEFT);
                tab.add(new Phrase("Relevé de prestations du "+Utils.smallDateFormat(startDate)+
                        " au "+Utils.smallDateFormat(endDate)), Element.ALIGN_CENTER);
                if(client==null)
                {
                    return "#";
                }
                tab.add(new Phrase(client.getInterventionType()), Element.ALIGN_RIGHT);
                
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
                final float tableSizes[]={10,11.5f,55,6,4.5f,13};
                table.setHeader(tableHeader);
                table.setWidths(tableSizes);
                table.setCellVerticalAlignment(Element.ALIGN_TOP);
                double total=0;
                int nbLineMax=firstPageMaxLine;
                int nbLineStep=0;
                /**
                 * 30 = header.setSpacingAfter(20);
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
                    if(nbLine%2==0)
                    {
                        color=Color.decode("#F2F5F9");
                    }
                    table.addBordered(new Paragraph(Utils.smallDateFormat(
                            i.getInterventionDate())),
                            Element.ALIGN_CENTER,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    String userName=user==null?"":user.getName();
                    if(userName.equalsIgnoreCase("CATHERINE"))
                    {
                        userName="MMMMMMMMMMMMM";
                    }
                    if(userName.length()>9)
                    {
                        userName=userName.substring(0,9);
                    }
                    table.addBordered(new Paragraph(userName),
                            Element.ALIGN_LEFT,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    table.addBordered(new Paragraph(i.getIdTask().getDescription()),
                            Element.ALIGN_LEFT,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    table.addBordered(new Paragraph(Utils.getDurationString(
                            Utils.getTimeFormat(i.getDuration()))),
                            Element.ALIGN_RIGHT,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    table.addBordered(new Paragraph(i.getDeplacement()?"Oui":"Non"),
                            Element.ALIGN_RIGHT,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    double deplacement=i.getDeplacement()?
                            i.getIdTask().getIdClient().getDeplacementValue():
                            0;
                    double tarif=i.getIdTask().getIdClient().getTarifValue()*i.getDuration()
                            +deplacement;
                    total+=tarif;
                    table.addBordered(new Paragraph(String.format("%.02f €", tarif)),
                            Element.ALIGN_RIGHT,
                            PDFTable.BOTTOM_BORDER,
                            color);
                    if((nbLine-nbLineStep)%nbLineMax==0)
                    {
                        pdf.add(table);
                        pdf.newPage(pageSize+table.getTotalHeight());
                        table=new PDFTable(6);
                        table.setHeader(tableHeader);
                        table.setWidths(tableSizes);
                        pageSize=0;
                        nbLineMax=othersPagesMaxLine;
                        nbLineStep=firstPageMaxLine;
                    }
                    nbLine++;
                }
                final String[][] footerList={
                    {"Total:",String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%.02f €", total),String.valueOf(Element.ALIGN_RIGHT)},
                };
                table.setFooter(footerList);
            }
            
            pdf.add(table);
            
            if(nbLine>firstPageMaxLine)
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
            written=true;
        }
        catch (MalformedURLException ex)
        {
//            ex.printStackTrace();
            written=false;
        }
        catch (BadElementException ex)
        {
//            ex.printStackTrace();
            written=false;
        }
        catch (IOException ex)
        {
//            ex.printStackTrace();
            written=false;
        }
        catch (DocumentException ex)
        {
//            ex.printStackTrace();
            written=false;
        }
        
        if(!written||pdf.isOpen())
        {
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF non créé",
                    "Erreur lors de l'écriture du PDF");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "#";
        }
        
        FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF Créé",
                "La création du PDF s'est terminée correctement");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "#";
    }
}
