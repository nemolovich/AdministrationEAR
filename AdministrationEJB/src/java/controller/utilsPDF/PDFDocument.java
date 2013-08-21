/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utilsPDF;

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
        this(pageSize, 36, 36, 36, 36);
    }

    public PDFDocument(Rectangle pageSize, float marginLeft, float marginRight,
            float marginTop, float marginBottom)
    {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        this.marginBottom=10;
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
        font.setSize(9);
        Font fontM = new Font();
        fontM.setSize(8.5f);
        f.setWidthPercentage(100);
        f.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        f.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        Paragraph beforeValue = new Paragraph(this.beforeFooter, font);
        Phrase pageValue = new Phrase("Page "+this.pages, fontM);
        Paragraph afterValue = new Paragraph(this.afterFooter, font);
        
        if(footerPageAlignment==Element.ALIGN_LEFT)
        {
            f.addBordered(pageValue, Element.ALIGN_LEFT, PDFTable.TOP_BORDER);
            f.addBordered(pageValue, PDFTable.TOP_BORDER);
            f.addBordered(afterValue, Element.ALIGN_RIGHT, PDFTable.TOP_BORDER);
        }
        else if(footerPageAlignment==Element.ALIGN_RIGHT)
        {
            f.addBordered(beforeValue, Element.ALIGN_LEFT, PDFTable.TOP_BORDER);
            f.addBordered(afterValue, PDFTable.TOP_BORDER);
            f.addBordered(pageValue, Element.ALIGN_RIGHT, PDFTable.TOP_BORDER);
        }
        else
        {
            f.addBordered(beforeValue, Element.ALIGN_LEFT, PDFTable.TOP_BORDER);
            f.addBordered(pageValue, PDFTable.TOP_BORDER);
            f.addBordered(afterValue, Element.ALIGN_RIGHT, PDFTable.TOP_BORDER);
        }
        
        f.setSpacingBefore(this.getPageSize().getHeight()-this.marginBottom
                -this.marginTop-(pageSize)-f.getTotalHeight()-15);
        
        this.add(f);
    }
}
