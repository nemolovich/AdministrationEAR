/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;

/**
 *
 * @author Brian GOHIER
 */
public class PDFDocument extends Document
{
    
    private int pages=0;
    private int footerPageAlignment=Element.ALIGN_CENTER;
    private String beforeFooter;
    private String afterFooter;

    public PDFDocument()
    {
        this(PageSize.A4);
    }

    public PDFDocument(Rectangle pageSize)
    {
        this(pageSize, 36, 36, 36, 10);
    }

    public PDFDocument(Rectangle pageSize, float marginLeft, float marginRight,
            float marginTop, float marginBottom)
    {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        this.pages=1;
    }
    
    public void setFooter(String beforeFooter, String afterFooter,
            int align)
    {
        this.beforeFooter=beforeFooter;
        this.afterFooter=afterFooter;
        this.footerPageAlignment=align;
    }
    
    @Override
    public boolean newPage()
    {
        return this.newPage(0);
    }
    
    public boolean newPage(float size)
    {
        try
        {
            this.addFooter(size);
        }
        catch (DocumentException ex)
        {}
        this.pages++;
        return super.newPage();
    }
    
    public void addFooter(float pageSize)
        throws DocumentException
    {
        PDFTable f=new PDFTable(3);
        f.setBorderWidthTop(.2f);
        Font font = new Font();
        font.setSize(11);
        Font fontM = new Font();
        fontM.setSize(8.5f);
        f.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        f.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        
        if(footerPageAlignment==Element.ALIGN_LEFT)
        {
            f.addBordered(new Phrase("Page "+this.pages,fontM), Element.ALIGN_LEFT, PDFTable.BORDER_TOP);
            f.addBordered(new Paragraph(this.beforeFooter,font), Element.ALIGN_CENTER, PDFTable.BORDER_TOP);
            f.addBordered(new Paragraph(this.afterFooter,fontM), Element.ALIGN_RIGHT, PDFTable.BORDER_TOP);
        }
        else if(footerPageAlignment==Element.ALIGN_RIGHT)
        {
            f.addBordered(new Paragraph(this.beforeFooter,fontM), Element.ALIGN_LEFT, PDFTable.BORDER_TOP);
            f.addBordered(new Paragraph(this.afterFooter,font), Element.ALIGN_CENTER, PDFTable.BORDER_TOP);
            f.addBordered(new Phrase("Page "+this.pages,fontM), Element.ALIGN_RIGHT, PDFTable.BORDER_TOP);
        }
        else
        {
            f.addBordered(new Paragraph(this.beforeFooter,fontM), Element.ALIGN_LEFT, PDFTable.BORDER_TOP);
            f.addBordered(new Phrase("Page "+this.pages,font), Element.ALIGN_CENTER, PDFTable.BORDER_TOP);
            f.addBordered(new Paragraph(this.afterFooter,fontM), Element.ALIGN_RIGHT, PDFTable.BORDER_TOP);
        }
        
        /**
         * 11 (FONT la plus haute) +2 (cellSpacingBefore) +5 (cellSpacingAfter)
         */
        float footerSize=18;
        float spacing=this.getPageSize().getHeight()-this.marginBottom
                -this.marginTop-(pageSize)-footerSize;
        f.setSpacingBefore(spacing);
        this.add(f);
    }
}
