package doit.shop.controller.Product.dto;

import doit.shop.repository.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryUpdateResponse(
        @Schema(description = "카테고리 식별 ID", example = "1")
        Long categoryId,
        @Schema(description = "카테고리 이름", example = "판타지")
        String categoryType
) {

    public static CategoryUpdateResponse from(Category category) {
        return CategoryUpdateResponse.builder()
                .categoryId(category.getId())
                .categoryType(category.getType())
                .build();
    }
}
