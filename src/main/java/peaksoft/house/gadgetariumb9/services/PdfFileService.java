package peaksoft.house.gadgetariumb9.services;

import com.lowagie.text.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
public interface PdfFileService {


    ResponseEntity<InputStreamResource> pdfFile(Long id) throws IOException, DocumentException;

    byte[] convertHtmlToPdf(String htmlContent) throws IOException, DocumentException;

}
