/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brian GOHIER
 */
public class PDFTable extends PdfPTable
{
    private List<PdfPCell> cellList=new ArrayList<PdfPCell>();
    private float borderWidthTop=.2f;
    private float borderWidthBottom=.2f;
    private float borderWidthLeft=0f;
    private float borderWidthRight=0f;
    private int cellVerticalAlignment=Element.ALIGN_MIDDLE;
    private int lines=0;
    public static final int BORDER_NO=0;
    public static final int BORDER_TOP=1;
    public static final int BORDER_BOTTOM=2;
    public static final int BORDER_BOTH=3;

    public PDFTable(int numColumns)
    {
        this(numColumns, 100);
    }
    
    public PDFTable(int numColumns, float widthPercentage)
    {
        super(numColumns);
        this.setWidthPercentage(widthPercentage);
        this.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
    }
    
    public void add()
    {
        this.add(Element.ALIGN_CENTER);
    }
    
    public void add(int align)
    {
        this.add(new Phrase(), align);
    }
    
    public void add(Element e)
    {
        this.add(e, Element.ALIGN_CENTER);
    }
    
    public void add(Element e, int align)
    {
        this.addCol(e, align, 1, PDFTable.BORDER_NO);
    }
    
    public void addBordered(Element e, int border)
    {
        this.addCol(e, Element.ALIGN_CENTER, 1, border);
    }
    
    public void addBordered(Element e, int align, int border)
    {
        this.addCol(e, align, 1, border);
    }
    
    public void addBordered(Element e, int align, int border, Color background)
    {
        this.addCol(e, align, 1, border, background);
    }
    
    public void addCol(Element e, int columns)
    {
        this.addCol(e, Element.ALIGN_CENTER, columns, PDFTable.BORDER_NO);
    }
    
    public void addCol(Element e, int align, int columns, int border)
    {
        this.addSize(e, align, columns, 2, 5, border);
    }
    
    public void addCol(Element e, int align, int columns, int border,
            Color background)
    {
        this.addSize(e, align, columns, 2, 5, border, background);
    }
    
    public void addSize(Element e, float spacingBefore, float spacingAfter)
    {
        this.addSize(e, Element.ALIGN_CENTER, 1, spacingBefore, spacingAfter);
    }
    
    public void addSize(Element e, int align, int columns, float spacingBefore,
            float spacingAfter)
    {
        this.addSize(e, align, columns, spacingBefore,
                spacingAfter, PDFTable.BORDER_NO, null, 1);
    }
    
    public void addSize(Element e, int align, int columns, float spacingBefore,
            float spacingAfter, int border)
    {
        this.addSize(e, align, columns, spacingBefore,
                spacingAfter, border, null, 1);
    }
    
    public void addSize(Element e, int align, int columns, float spacingBefore,
            float spacingAfter, int border, Color background)
    {
        this.addSize(e, align, columns, spacingBefore,
                spacingAfter, border, background, 1);
    }
    
    public void addSize(Element e, int align, int columns, float spacingBefore,
            float spacingAfter, int border, Color background, float gray)
    {
        PdfPCell cell = null;
        if(e instanceof Image)
        {
            cell=new PdfPCell((Image)e);
        }
        else if(e instanceof Phrase)
        {
            cell=new PdfPCell((Phrase)e);
        }
        else if(e instanceof PdfPTable)
        {
            cell=new PdfPCell((PdfPTable)e);
        }
        else if(e instanceof PdfPCell)
        {
            cell=new PdfPCell((PdfPCell)e);
        }
        if(cell==null)
        {
            return;
        }
        cell.setBorder(Rectangle.NO_BORDER);
        if(border==PDFTable.BORDER_TOP)
        {
            cell.setBorderWidthTop(this.borderWidthTop);
        }
        else if(border==PDFTable.BORDER_BOTTOM)
        {
            cell.setBorderWidthBottom(this.borderWidthBottom);
        }
        else if(border==PDFTable.BORDER_BOTH)
        {
            cell.setBorderWidthTop(this.borderWidthTop);
            cell.setBorderWidthBottom(this.borderWidthBottom);
        }
        else if(border!=PDFTable.BORDER_NO)
        {
            cell.setBorderWidthTop(this.borderWidthTop);
            cell.setBorderWidthBottom(this.borderWidthBottom);
            cell.setBorderWidthLeft(this.borderWidthLeft);
            cell.setBorderWidthRight(this.borderWidthRight);
        }
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(this.cellVerticalAlignment);
        cell.setPaddingTop(spacingBefore);
        cell.setPaddingBottom(spacingAfter);
        cell.setGrayFill(gray);
        if(background!=null)
        {
            cell.setBackgroundColor(background);
        }
        cell.setColspan(columns);
        super.addCell(cell);
        this.cellList.add(cell);
        this.lines++;
    }

    public void setHeader(String[][] tableHeader)
    {
        Font headerFont = 
            FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
        if(tableHeader.length<this.getNumberOfColumns())
        {
            throw new IndexOutOfBoundsException("Le nombre de titres est trop court");
        }
        for(String[] title:tableHeader)
        {
            if(title.length<2)
            {
                throw new IndexOutOfBoundsException("Le paramètre de titre est incorrect");
            }
            Paragraph cell=new Paragraph(title[0],headerFont);
            int align=this.cellVerticalAlignment;
            this.cellVerticalAlignment=Element.ALIGN_TOP;
            this.addSize(cell, Integer.valueOf(title[1]), 1, 5, 10,
                    PDFTable.BORDER_BOTH, null, 0.9f);
            this.cellVerticalAlignment=align;
        }
        this.setHeaderRows(1);
    }
    
    public void setFooter(String[][] listFooter)
    {
        this.setFooter(listFooter, PDFTable.BORDER_BOTH);
    }
    
    public void setFooter(String[][] listFooter, int borders)
    {
        Font footerFont = 
            FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
        if(listFooter.length==0)
        {
            throw new IndexOutOfBoundsException("Nombre de paramètres pour footer incorrect");
        }
        Paragraph cell=new Paragraph(listFooter[0][0],footerFont);
        float before=5;
        float after=10;
        if(borders==PDFTable.BORDER_TOP)
        {
            after=2;
        }
        else if(borders==PDFTable.BORDER_NO)
        {
            before=0;
            after=2;
        }
        else if(borders==PDFTable.BORDER_BOTTOM)
        {
            before=0;
        }
        this.addSize(cell,Integer.valueOf(listFooter[0][1]),
                this.getNumberOfColumns()-listFooter.length+1,
                before, after, borders, null, 0.9f);
        for(int i=1;i<listFooter.length;i++)
        {
            cell=new Paragraph(listFooter[i][0],footerFont);
            this.addSize(cell,Integer.valueOf(listFooter[i][1]),1,
                    before, after, borders, null, 0.9f);
        }
    }
    
    public PdfPCell getCell(int i, int j)
    {
        return this.cellList.get(((i-1)*this.getNumberOfColumns()+(j-1)));
    }
    
    public PdfPCell getCell(int i)
    {
        return this.cellList.get(i);
    }

    public List<PdfPCell> getCellList() {
        return cellList;
    }

    public void setCellList(List<PdfPCell> cellList) {
        this.cellList = cellList;
    }

    public float getBorderWidthTop() {
        return borderWidthTop;
    }

    public void setBorderWidthTop(float borderWidthTop) {
        this.borderWidthTop = borderWidthTop;
    }

    public float getBorderWidthBottom() {
        return borderWidthBottom;
    }

    public void setBorderWidthBottom(float borderWidthBottom) {
        this.borderWidthBottom = borderWidthBottom;
    }

    public float getBorderWidthLeft() {
        return borderWidthLeft;
    }

    public void setBorderWidthLeft(float borderWidthLeft) {
        this.borderWidthLeft = borderWidthLeft;
    }

    public float getBorderWidthRight() {
        return borderWidthRight;
    }

    public void setBorderWidthRight(float borderWidthRight) {
        this.borderWidthRight = borderWidthRight;
    }

    public int getCellVerticalAlignment() {
        return cellVerticalAlignment;
    }

    public void setCellVerticalAlignment(int cellVerticalAlignment) {
        this.cellVerticalAlignment = cellVerticalAlignment;
    }
}
