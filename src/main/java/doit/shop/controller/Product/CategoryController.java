package doit.shop.controller.Product;

import doit.shop.controller.Product.dto.CategoryUpdateRequest;
import doit.shop.controller.Product.dto.CategoryUpdateResponse;
import doit.shop.controller.Product.dto.CategoryRegisterRequest;
import doit.shop.controller.Product.dto.CategoryRegisterResponse;
import doit.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/products/categories")
    public List<CategoryRegisterResponse> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public CategoryRegisterResponse registerCategory(@RequestBody CategoryRegisterRequest categoryRegisterRequest) {
        return categoryService.registerCategory(categoryRegisterRequest);
    }

    @PutMapping("/categories/{categoryId}")
    public CategoryUpdateResponse modifyCategory(@PathVariable Long categoryId, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return categoryService.updateCategory(categoryId, categoryUpdateRequest);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
