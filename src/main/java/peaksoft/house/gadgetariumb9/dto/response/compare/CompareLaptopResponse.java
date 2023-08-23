package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;
import java.math.BigDecimal;

@SuperBuilder
@NoArgsConstructor
public class CompareLaptopResponse extends CompareProductResponse{

    private Processor processor;

    private Purpose purpose;

    private double screen_size;

    private int video_memory;

    public CompareLaptopResponse(Long subProductId, Long prId, String brandName, String prodName,
                                 BigDecimal price, String color, String screen, int rom,
                                 String operationalSystems, String catTitle, String subCatTitle,
                                 String image, Processor processor, Purpose purpose,
                                 double screen_size, int video_memory) {
        super(subProductId, prId, brandName, prodName, price, color, screen, rom, operationalSystems, catTitle, subCatTitle, image);
        this.processor = processor;
        this.purpose = purpose;
        this.screen_size = screen_size;
        this.video_memory = video_memory;
    }
}
