package doit.shop.controller.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryRegisterRequest(
        @Schema(description = "카테고리 이름", example = "판타지")
        String categoryType
) {
}
