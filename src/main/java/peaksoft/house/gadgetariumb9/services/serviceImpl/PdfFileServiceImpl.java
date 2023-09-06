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

        context.setVariable("name", subProduct.getProduct().getName());
        context.setVariable("brand", subProduct.getProduct().getBrand().getName());
        context.setVariable("category", subProduct.getProduct().getCategory().getTitle());
        context.setVariable("color", subProduct.getCodeColor());
        context.setVariable("price", subProduct.getPrice());
        context.setVariable("discount", subProduct.getDiscount().getSale());
        context.setVariable("created_at", subProduct.getProduct().getCreatedAt());
        context.setVariable("rom", subProduct.getRom());
        context.setVariable("ram", subProduct.getRam());
        context.setVariable("image", subProduct.getImages().stream().findAny().orElseThrow(() -> new NotFoundException("Найти изображение недействительно или не найдено!!")));

        String processedHtml = templateEngine.process(template, context);

        byte[] pdfBytes = convertHtmlToPdf(processedHtml);
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
    public byte[] convertHtmlToPdf(String htmlContent) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}