package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class ComparisonCountResponse {

    private String categoryTitle;

    private  int totalCounter;

    public ComparisonCountResponse(String categoryTitle, int totalCounter) {
        this.categoryTitle = categoryTitle;
        this.totalCounter = totalCounter;
    }
}
