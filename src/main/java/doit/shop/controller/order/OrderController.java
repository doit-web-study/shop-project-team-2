package doit.shop.controller.order;

import doit.shop.controller.order.dto.OrderCreateRequest;
import doit.shop.controller.order.dto.OrderInfoResponse;
import doit.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderInfoResponse createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        try {
            return orderService.createOrder(orderCreateRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

    @GetMapping("/user/{userId}")
    public List<OrderInfoResponse> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}")
    public OrderInfoResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    public OrderInfoResponse cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

}
