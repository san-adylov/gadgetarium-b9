package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import java.util.List;

public interface BasketTemplate {

    List<BasketResponse>  getAllByProductsFromTheBasket ();

    BasketInfographicResponse getInfographic();
}
