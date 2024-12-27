package doit.shop.controller.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderItemCreateRequest(
        @Schema(description = "상품 ID", example = "1")
        Long productId,
        @Schema(description = "주문 수량", example = "2")
        int quantity
) {
}
