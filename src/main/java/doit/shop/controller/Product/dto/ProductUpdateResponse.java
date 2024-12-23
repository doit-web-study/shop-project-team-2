package doit.shop.controller.Product.dto;

import doit.shop.repository.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProductUpdateResponse(
        @Schema(description = "상품 ID", example = "1")
        Long productId,

        @Schema(description = "상세 상태 코드", example = "400001")
        String detailStatusCode,

        @Schema(description = "메시지", example = "상품 수정 성공!")
        String message
) {

    public static ProductUpdateResponse from(Product product) {
        return ProductUpdateResponse.builder()
                .productId(product.getId())
                .detailStatusCode(null)
                .message("상품 수정 성공!")
                .build();
    }

    public static ProductUpdateResponse fromError(String detailStatusCode, String message) {
        return ProductUpdateResponse.builder()
                .productId(null)
                .detailStatusCode(detailStatusCode)
                .message(message)
                .build();
    }
}
