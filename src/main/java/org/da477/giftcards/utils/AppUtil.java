package org.da477.giftcards.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppUtil {

    private AppUtil() {
    }

    public static byte[] getPdfContent(long number) throws IOException {
        File tmpFile = File.createTempFile(String.valueOf(number), "pdf");
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(tmpFile));
            document.open();

            Paragraph paragraph = new Paragraph("Gift Card № " + number);
            document.add(paragraph);
            document.close();

            byte[] pdfContent = Files.readAllBytes(Path.of(tmpFile.toURI()));
            tmpFile.delete();
            return pdfContent;

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
