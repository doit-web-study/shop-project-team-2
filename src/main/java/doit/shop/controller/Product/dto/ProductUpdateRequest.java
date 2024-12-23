package doit.shop.controller.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record ProductUpdateRequest(
        @Schema(description = "상품 이름", example = "Fantasy Book")
        String productName,

        @Schema(description = "상품 설명", example = "A thrilling fantasy novel.")
        String productDescription,

        @Schema(description = "상품 가격", example = "15000")
        Integer productPrice,

        @Schema(description = "상품 재고", example = "100")
        Integer productStock,

        @Schema(description = "상품 이미지 파일")
        MultipartFile productImage,

        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId
) {
}
