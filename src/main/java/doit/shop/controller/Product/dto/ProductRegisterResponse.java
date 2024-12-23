package doit.shop.controller.Product.dto;

import doit.shop.repository.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProductRegisterResponse(
        @Schema(description = "상품 ID", example = "1")
        Long productId,

        @Schema(description = "상세 상태 코드", example = "400001")
        String detailStatusCode,

        @Schema(description = "메시지", example = "상품 이름, 상품 설명, 상품 가격, 상품 재고, 카테고리 ID는 필수로 입력해주셔야 합니다.")
        String message
) {

    public static ProductRegisterResponse from(Product product) {
        return ProductRegisterResponse.builder()
                .productId(product.getId())
                .detailStatusCode(null)
                .message("상품 등록 성공")
                .build();
    }

    public static ProductRegisterResponse fromError(String detailStatusCode, String message) {
        return ProductRegisterResponse.builder()
                .productId(null)
                .detailStatusCode(detailStatusCode)
                .message(message)
                .build();
    }
}
