package com.xinyuan.haze.lucene.file.reader;

import com.xinyuan.haze.file.utils.FileManager;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;

/**
 * Created by Sofar on 2016/10/20.
 */
public class CommonFileReader implements FileReader {

    @Override
    public StringReader getContent(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)){
            String fileExtension = FileManager.getExtension(path).toUpperCase();
            FileReaderType fileReaderType = FileReaderType.valueOf(fileExtension);
            switch (fileReaderType) {
                case DOC:
                    WordExtractor wordExtractor = new WordExtractor(is);
                    return new StringReader(wordExtractor.getText());
                case DOCX:
                    XWPFDocument document = new XWPFDocument(is);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                    return new StringReader(extractor.getText());
                case PDF:
                    try(PDDocument pdfDocument = PDDocument.load(is);) {
                        StringWriter writer = new StringWriter();
                        PDFTextStripper stripper = new PDFTextStripper();
                        stripper.writeText(pdfDocument, writer);
                        String contents = writer.getBuffer().toString();
                        //PDDocumentInformation info = pdfDocument.getDocumentInformation();
                        return new StringReader(contents);
                    }
                default:
                    return new StringReader(new String(Files.readAllBytes(path)));
            }
        }
    }

    public enum FileReaderType {
        DOC,
        DOCX,
        PDF,
        TXT,
        NONE,
        SQL;
    }
}
