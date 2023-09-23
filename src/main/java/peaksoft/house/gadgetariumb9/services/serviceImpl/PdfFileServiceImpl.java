package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.PdfFileService;
import java.io.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfFileServiceImpl implements PdfFileService {

    private final SubProductRepository subProductRepository;
    private final TemplateEngine templateEngine;

    @Override
    public ResponseEntity<InputStreamResource> pdfFile(Long id) throws IOException, DocumentException {
        String template = "templates/pdf-file-template.html";
        Context context = new Context();

        SubProduct subProduct = subProductRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("SubProduct with id: " + id + " is not found");
                    return new NotFoundException("SubProduct with id: " + id + " is not found");
                });

        context.setVariable("name", subProduct.getProduct() != null ? subProduct.getProduct().getName() : "N/A");
        context.setVariable("brand", subProduct.getProduct() != null && subProduct.getProduct().getBrand() != null ? subProduct.getProduct().getBrand().getName() : "N/A");
        context.setVariable("category", subProduct.getProduct() != null && subProduct.getProduct().getCategory() != null ? subProduct.getProduct().getCategory().getTitle() : "N/A");
        context.setVariable("color", subProduct.getCodeColor() != null ? subProduct.getCodeColor() : "N/A");
        context.setVariable("price", subProduct.getPrice() != null ? subProduct.getPrice() : "N/A");
        context.setVariable("discount", subProduct.getDiscount() != null ? subProduct.getDiscount().getSale() : "N/A");
        context.setVariable("created_at", subProduct.getProduct() != null && subProduct.getProduct().getCreatedAt() != null ? subProduct.getProduct().getCreatedAt() : "N/A");
        context.setVariable("rom", subProduct.getRom());
        context.setVariable("ram", subProduct.getRam());
        context.setVariable("image", subProduct.getImages() != null && !subProduct.getImages().isEmpty() ? subProduct.getImages().stream().findAny().orElse("N/A") : "N/A");


        String processedHtml = templateEngine.process(template, context);

        byte[] pdfBytes = convertHtmlToPdf(processedHtml).getBody();
        InputStream inputStream = new ByteArrayInputStream(pdfBytes);
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Override
    public ResponseEntity<byte[]> convertHtmlToPdf(String htmlContent) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}