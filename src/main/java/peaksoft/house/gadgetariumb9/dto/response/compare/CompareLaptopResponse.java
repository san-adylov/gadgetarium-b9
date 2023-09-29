package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;

@SuperBuilder
@NoArgsConstructor
@Getter
public class CompareLaptopResponse extends CompareProductResponse{

    private Processor processor;

    private Purpose purpose;

    private double screen_size;

    private int video_memory;

}
