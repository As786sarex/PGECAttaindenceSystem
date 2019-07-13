package com.wildcardenter.myfab.pgecattaindencesystem;

import android.content.Intent;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void generate() {
        try {
            String filename = System.currentTimeMillis() + ".pdf";
            File file = new File(filename);
            PdfWriter writer = new PdfWriter(file);

            PdfDocument document = new PdfDocument(writer);
            Document pdf = new Document(document);
            float[] colomWidth = {50f, 100f, 50f, 250f};
            Table table = new Table(colomWidth, true).setBackgroundColor(DeviceGray.GRAY);
            table.setMarginTop(60f);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.addHeaderCell(new Cell().add(new Paragraph("Roll No")
                    .setFontColor(DeviceGray.WHITE)).setBackgroundColor(new DeviceRgb(83, 109, 254))
                    .setTextAlignment(TextAlignment.CENTER).setMinWidth(200));
            table.addHeaderCell(new Cell().add(new Paragraph("no Of Class")
                    .setFontColor(DeviceGray.WHITE)).setBackgroundColor(new DeviceRgb(83, 109, 254))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Percentage")
                    .setFontColor(DeviceGray.WHITE)).setBackgroundColor(new DeviceRgb(83, 109, 254))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("List of Dates  of class attended")
                    .setFontColor(DeviceGray.WHITE)).setBackgroundColor(new DeviceRgb(83, 109, 254))
                    .setTextAlignment(TextAlignment.CENTER));
            for (int i = 0; i < 6; i++) {

                table.addCell(new Cell().add(new Paragraph(String.valueOf(i))).setMinWidth(200));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
                com.itextpdf.layout.element.List listt = new com.itextpdf.layout.element.List().setKeepTogether(true);
                for (int j = 0; j < 6; j++) {
                    listt.add(String.valueOf(i)).setWidth(300);
                }
                table.addCell(new Cell().add(new Paragraph(((float) i / 5.0) * 100 + "%")));
                table.addCell(listt);
            }
            table.addFooterCell(new Cell().setBackgroundColor(new DeviceRgb(487, 187, 205)));
            table.addFooterCell(new Cell().setBackgroundColor(new DeviceRgb(487, 187, 205)));
            table.addFooterCell(new Cell().setBackgroundColor(new DeviceRgb(487, 187, 205)));
            table.addFooterCell(new Cell().setBackgroundColor(new DeviceRgb(487, 187, 205)));
            pdf.add(table);
            pdf.close();
            assertEquals(89, 89);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCoin(){
        String input="5 7 2 7 5 2 5";
        StringTokenizer tokenizer=new StringTokenizer(input);
        Set<Integer> set=new HashSet<>();
        while (tokenizer.hasMoreTokens()){
            int a= Integer.parseInt(tokenizer.nextToken());
            if (!set.contains(a)){
                set.add(a);
            }
            else{
                set.remove(a);
            }
        }
        set.forEach(i->assertEquals(String.valueOf(5),String.valueOf(i)));

    }
}