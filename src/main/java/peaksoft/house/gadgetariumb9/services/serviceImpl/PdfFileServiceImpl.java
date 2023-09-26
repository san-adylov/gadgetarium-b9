package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.PdfFileService;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfFileServiceImpl implements PdfFileService {

    private final SubProductRepository subProductRepository;

    @Override
    public void generatePdf(Long subProductId, HttpServletResponse response)
            throws IOException {
        SubProduct subProduct = subProductRepository.findById(subProductId)
                .orElseThrow(() -> {
                    log.error("SubProduct with id: " + subProductId + " is not found");
                    return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
                });

        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");
        response.setContentType("application/pdf");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));

        try (Document document = new Document(pdfDoc)) {
            String imageUrl = subProduct.getImages().get(0);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Image image = new Image(ImageDataFactory.create(new URL(imageUrl)));
                image.scaleToFit(300, 300);
                document.add(image);
                document.add(new Paragraph("\n"));
            }

            PdfFont normalFont = PdfFontFactory.createFont("Helvetica");

            Text titleText = new Text(subProduct.getProduct().getName())
                    .setFont(normalFont)
                    .setFontSize(18)
                    .setFontColor(new DeviceRgb(0, 0, 0));

            Div brandDiv = new Div()
                    .add(new Paragraph("Brand: " + subProduct.getProduct().getBrand().getName())
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            Div categoryDiv = new Div()
                    .add(new Paragraph("Category: " + subProduct.getProduct().getCategory().getTitle())
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            Div colorDiv = new Div()
                    .add(new Paragraph("Color: " + subProduct.getCodeColor())
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            Div priceDiv = new Div()
                    .add(new Paragraph("Price: " + subProduct.getPrice() + " KGS")
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            document.add(new Paragraph().add(titleText));
            document.add(brandDiv);
            document.add(categoryDiv);
            document.add(colorDiv);
            document.add(priceDiv);

            if (subProduct.getDiscount() != null) {
                Div saleDiv = new Div()
                        .add(new Paragraph("Sale: " + subProduct.getDiscount().getSale() + "%")
                                .setFont(normalFont)
                                .setFontSize(14)
                                .setFontColor(new DeviceRgb(0, 0, 0))
                                .setBold());

                document.add(saleDiv);
            }

            Div romDiv = new Div()
                    .add(new Paragraph("ROM: " + subProduct.getRom() + " GB")
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            Div ramDiv = new Div()
                    .add(new Paragraph("RAM: " + subProduct.getRam() + " GB")
                            .setFont(normalFont)
                            .setFontSize(14)
                            .setFontColor(new DeviceRgb(0, 0, 0))
                            .setBold());

            document.add(romDiv);
            document.add(ramDiv);
        } catch (Exception e) {
            log.error("Error adding content to PDF: " + e.getMessage());
        }
    }

}