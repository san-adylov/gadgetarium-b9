package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.category.SubCategoryResponse;
import peaksoft.house.gadgetariumb9.models.Category;
import peaksoft.house.gadgetariumb9.repositories.CategoryRepository;
import peaksoft.house.gadgetariumb9.services.CategoryService;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        log.info("Get all categories");
        return categoryRepository.findAll();
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategories(Long categoryId) {
        log.info("Get all sub categories by category id");
        return categoryRepository.getAllSubCategory(categoryId);
    }
}
