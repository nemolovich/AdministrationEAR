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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import controller.utilsPDF.PDFTable;
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
    
    public void createPDF(String factureNumber,List<Intervention> list)
    {
        PDFDocument pdf=new PDFDocument(PageSize.A4.rotate());
        File pdfFile=new File(Utils.getResourcesPath()+"generated"+File.separator+
                "facture"+File.separator+"Facture_"+factureNumber+".pdf");
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
            return;
        }
        
        boolean written;
        try
        {
            pdf.open();
            pdf.setFooter("","Détails de la facture n°"+factureNumber, Element.ALIGN_RIGHT);
            
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String logo = servletContext.getRealPath("")+File.separator+
                    "resources"+File.separator+"img"+File.separator+"logoAS.png";
            Image logoImg = Image.getInstance(logo);
            logoImg.scaleAbsolute(100, 57);
            
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
            header.setSpacingAfter(30);
            
            logoImg.setAlignment(Element.ALIGN_LEFT);
            PdfPCell leftTop=new PdfPCell(logoImg);
            leftTop.setBorder(Rectangle.NO_BORDER);
            
            PdfPCell rightTop=new PdfPCell(new Paragraph("Facture n°"+factureNumber));
            rightTop.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightTop.setBorder(Rectangle.NO_BORDER);
            
            PdfPCell leftBottom=new PdfPCell(new Paragraph("8 Rue Louise WEISS\n37700 La Ville aux Dames"));
            leftBottom.setBorder(Rectangle.NO_BORDER);
            
            PdfPCell rightBottom=new PdfPCell();
            rightBottom.setBorder(Rectangle.NO_BORDER);
            
            header.addCell(leftTop);
            header.addCell(rightTop);
            header.addCell(leftBottom);
            header.addCell(rightBottom);
            
            pdf.add(header);
            PDFTable table;
            Paragraph details = new Paragraph();
            int nbLine=1;
            float pageSize;
            boolean none=false;
            if(list==null||list.isEmpty())
            {
                table=new PDFTable(1);
                table.add(new Paragraph("Aucune intervention"));
                none=true;
            }
            else
            {
                Intervention item=list.get(0);
                Client client=item.getIdTask().getIdClient();
                String societyName=client.getName();
                Font fontB = new Font();
                fontB.setStyle(Font.BOLD);
                Font font = new Font();
                Phrase ph0=new Phrase("Facture pour la société "+
                        societyName+"",fontB);
                Phrase ph1=new Phrase(new Chunk("Tarif de l'intervention:",font));
                Paragraph ph2=new Paragraph(String.format(" %.02f €", client.getTarifValue()),fontB);
                Phrase ph3=new Phrase(new Chunk("Tarif déplacement:",font));
                Paragraph ph4=new Paragraph(String.format(" %.02f €", client.getDeplacementValue()),fontB);
                PDFTable tab1=new PDFTable(2);
                tab1.setWidthPercentage(35);
                final float[] sizes={70,30};
                tab1.setWidths(sizes);
                tab1.setHorizontalAlignment(Element.ALIGN_LEFT);
                tab1.addSize(ph1,Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph2,Element.ALIGN_RIGHT, 1, 0, 0);
                tab1.addSize(ph3,Element.ALIGN_LEFT, 1, 0, 0);
                tab1.addSize(ph4,Element.ALIGN_RIGHT, 1, 0, 0);
                tab1.setSpacingAfter(0);
                tab1.setSpacingBefore(0);
                details.add(ph0);
                details.add(tab1);
                details.setSpacingBefore(0);
                details.setSpacingAfter(20);
                pdf.add(details);
                Collections.sort(list);
                table=new PDFTable(4);
                final String[][] tableHeader={
                    {"Date:",    String.valueOf(Element.ALIGN_LEFT)},
                    {"Durée:",   String.valueOf(Element.ALIGN_LEFT)},
                    {"Déplacement:",   String.valueOf(Element.ALIGN_RIGHT)},
                    {"Tarif:",   String.valueOf(Element.ALIGN_RIGHT)}};
                table.setHeader(tableHeader);
                double total=0;
                int nbLineMax=15;
                int nbLineStep=0;
                pageSize=header.getTotalHeight()+details.getTotalLeading()+30+
                        65;
                for(int j=0;j<14;j++)
                {
                    for(Intervention i:list)
                    {
                        Color color=null;
                        if(nbLine%2==0)
                        {
                            color=Color.decode("#F2F5F9");
                        }
                        table.addBordered(new Paragraph(Utils.smallDateFormat(
                                i.getInterventionDate())),
                                Element.ALIGN_LEFT,
                                PDFTable.BOTTOM_BORDER,
                                color);
                        table.addBordered(new Paragraph(Utils.getDurationString(
                                Utils.getTimeFormat(i.getDuration()))),
                                Element.ALIGN_LEFT,
                                PDFTable.BOTTOM_BORDER,
                                color);
                        table.addBordered(new Paragraph(i.getDeplacement()?"Oui":"Non"),
                                Element.ALIGN_RIGHT,
                                PDFTable.BOTTOM_BORDER,
                                color);
                        double tarif=i.getIdTask().getIdClient().getTarifValue()*i.getDuration()
                                +i.getIdTask().getIdClient().getDeplacementValue();
                        total+=tarif;
                        table.addBordered(new Paragraph(String.format("%.02f €", tarif)),
                                Element.ALIGN_RIGHT,
                                PDFTable.BOTTOM_BORDER,
                                color);
                        if((nbLine-nbLineStep)%nbLineMax==0)
                        {
                            pdf.add(table);
                            pdf.newPage(pageSize+table.getTotalHeight());
                            table=new PDFTable(4);
                            table.setHeader(tableHeader);
                            pageSize=5;
                            nbLineMax=25;
                            nbLineStep=15;
                        }
                        nbLine++;
                    }
                }
                final String[][] footerList={
                    {"Total:",String.valueOf(Element.ALIGN_RIGHT)},
                    {String.format("%.02f €", total),String.valueOf(Element.ALIGN_RIGHT)},
                };
                table.setFooter(footerList);
            }
            
            pdf.add(table);
            
            if(nbLine>20)
            {
                pageSize=table.getTotalHeight()+5;
            }
            else
            {
                pageSize=header.getTotalHeight()+details.getTotalLeading()+65+
                        table.getTotalHeight()+30;
                if(none)
                {
                    pageSize-=75;
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
            return;
        }
        
        FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF Créé",
                "La création du PDF s'est terminée correctement");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
