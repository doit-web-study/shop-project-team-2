package doit.shop.service;

import doit.shop.controller.Product.dto.CategoryUpdateRequest;
import doit.shop.controller.Product.dto.CategoryUpdateResponse;
import doit.shop.controller.Product.dto.CategoryRegisterRequest;
import doit.shop.controller.Product.dto.CategoryRegisterResponse;
import doit.shop.repository.Category;
import doit.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryRegisterResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryRegisterResponse> responses = new ArrayList<>();
        for(Category category: categories) {
            responses.add(CategoryRegisterResponse.from(category));
        }
        return responses;
    }

    @Transactional
    public CategoryRegisterResponse registerCategory(CategoryRegisterRequest categoryRegisterRequest) {
        Category category = new Category(categoryRegisterRequest.categoryType());
        Category registeredCategory = categoryRepository.save(category);
        return CategoryRegisterResponse.from(registeredCategory);
    }

    @Transactional
    public CategoryUpdateResponse updateCategory(Long categoryId, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found!"));
        category.updateType(categoryUpdateRequest.categoryType());
        return CategoryUpdateResponse.from(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found!"));
        categoryRepository.delete(category);
    }
}
