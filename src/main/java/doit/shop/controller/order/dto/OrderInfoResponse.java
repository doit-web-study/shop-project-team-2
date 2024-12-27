package doit.shop.controller.order.dto;

import doit.shop.repository.Order;
import doit.shop.repository.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record OrderInfoResponse(
        @Schema(description = "주문 ID", example = "1")
        Long orderId,
        @Schema(description = "회원 ID", example = "123")
        Long userId,
        @Schema(description = "주문 상태", example = "ORDER")
        OrderStatus orderStatus,
        @Schema(description = "주문 총 금액", example = "100000")
        Integer totalPrice,
        @Schema(description = "주문 아이템 목록")
        List<OrderItemInfoResponse> orderItems
) {

    public static OrderInfoResponse from(Order order) {
        List<OrderItemInfoResponse> orderItemResponses = order.getOrderItems().stream()
                .map(OrderItemInfoResponse::from)
                .collect(Collectors.toList());

        return OrderInfoResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // 주문한 회원 ID
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemResponses)
                .build();
    }
}