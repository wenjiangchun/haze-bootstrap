package com.xinyuan.haze.lucene.file.reader;

import com.sun.nio.file.ExtendedOpenOption;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Sofar on 2016/10/20.
 */
public class CommonFileReaderTest {
    @Test
    public void getContent() throws Exception {

       /* CommonFileReader reader = new CommonFileReader();
        System.out.println(reader.getContent(Paths.get("d:\\111.sql")));*/

        System.out.println(Files.exists(Paths.get("d:\\serverStartup.pdf")));
        try(InputStream is = Files.newInputStream(Paths.get("d:\\222.pdf"), ExtendedOpenOption.NOSHARE_READ);) {
            PDDocument pdfDocument = PDDocument.load(is);
            StringWriter writer = new StringWriter();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.writeText(pdfDocument, writer);
            String contents = writer.getBuffer().toString();
            StringReader reader = new StringReader(contents);
            PDDocumentInformation info = pdfDocument.getDocumentInformation();
            IOUtils.copy(reader, System.out);
            pdfDocument.close();
        }


    }

}