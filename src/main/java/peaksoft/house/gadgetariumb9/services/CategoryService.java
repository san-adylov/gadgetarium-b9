package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.category.SubCategoryResponse;
import peaksoft.house.gadgetariumb9.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<SubCategoryResponse> getAllSubCategories(Long categoryId);
}
