/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Files;
import bean.Utils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import controller.utils.PDFDocument;
import controller.utils.PDFTable;
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
            
            Paragraph leftBottom=new Paragraph();
            leftBottom.add(new Paragraph("8 Rue Louise WEISS\n"
                    + "37700 La Ville aux Dames"));
            Font littleFont=FontFactory.getFont(FontFactory.HELVETICA, 8.5f, Font.NORMAL);
            Paragraph tel=new Paragraph("                            Tél: 02.47.44.71.14",littleFont);
            leftBottom.add(tel);
            header.addSize(leftBottom, Element.ALIGN_LEFT, 1, 0, 0);
            
            Intervention item;
            Client client = null;
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
                            (nbLines+1-multiLines>=list.size()&&
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
