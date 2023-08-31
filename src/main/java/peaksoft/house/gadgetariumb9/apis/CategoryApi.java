package peaksoft.house.gadgetariumb9.apis;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.response.category.SubCategoryResponse;
import peaksoft.house.gadgetariumb9.models.Category;
import peaksoft.house.gadgetariumb9.services.CategoryService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "Category API", description = "API for category managment")
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Retrieve a list of all available categories.")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("get-sub-categories")
    @Operation(summary = "Get All Subcategories", description = "Retrieve a list of all subcategories for a given category.")
    public List<SubCategoryResponse> getAllSubCategories(@RequestParam Long categoryId){
        return categoryService.getAllSubCategories(categoryId);
    }

}
