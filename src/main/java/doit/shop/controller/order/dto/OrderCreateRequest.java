package doit.shop.controller.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record OrderCreateRequest(
        @Schema(description = "유저 식별 ID", example = "1")
        Long userId,
        @Schema(description = "주문 항목 리스트", example = "\"[{\\\"productId\\\":1, \\\"quantity\\\":2}, {\\\"productId\\\":2, \\\"quantity\\\":1}]\"")
        List<OrderItemCreateRequest> orderItems
) {
}
