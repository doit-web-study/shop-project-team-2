package doit.shop.controller.order.dto;

import doit.shop.repository.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrderItemInfoResponse(
        @Schema(description = "주문 아이템 ID", example = "1")
        Long orderItemId,
        @Schema(description = "상품 이름", example = "해리포터 책")
        String productName,
        @Schema(description = "주문 수량", example = "2")
        Integer quantity,
        @Schema(description = "상품 가격", example = "10000")
        Integer orderPrice
) {

    public static OrderItemInfoResponse from(OrderItem orderItem) {
        return OrderItemInfoResponse.builder()
                .orderItemId(orderItem.getId())
                .productName(orderItem.getProduct().getName())  // 상품 이름
                .quantity(orderItem.getQuantity())  // 주문 수량
                .orderPrice(orderItem.getOrderPrice())  // 주문 가격
                .build();
    }
}