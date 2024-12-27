package doit.shop.service;

import doit.shop.controller.order.dto.OrderCreateRequest;
import doit.shop.controller.order.dto.OrderInfoResponse;
import doit.shop.controller.order.dto.OrderItemCreateRequest;
import doit.shop.repository.User;
import doit.shop.repository.UserRepository;
import doit.shop.repository.Order;
import doit.shop.repository.OrderItem;
import doit.shop.repository.OrderRepository;
import doit.shop.repository.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderInfoResponse createOrder(OrderCreateRequest orderCreateRequest){
        User user = userRepository.findById(orderCreateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("일치하는 회원이 없습니다."));

        List<OrderItem> orderItems = new ArrayList<>();
        Integer totalPrice = 0;

        for (OrderItemCreateRequest orderItemRequest : orderCreateRequest.orderItems()) {
            Product product = productRepository.findById(orderItemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(orderItemRequest.quantity())
                    .orderPrice(product.getPrice() * orderItemRequest.quantity())
                    .build();

            orderItems.add(orderItem);

            totalPrice += orderItem.getOrderPrice();
        }

        Order order = Order.builder()
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDER)
                .user(user)
                .build();

        Order savedOrder = orderRepository.save(order);

        return OrderInfoResponse.from(savedOrder);
    }

    public OrderInfoResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return OrderInfoResponse.from(order);
    }

    public List<OrderInfoResponse> getOrdersByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 유저가 없습니다."));

        List<Order> orders = orderRepository.findByUser(user);

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        }

        return orders.stream()
                .map(OrderInfoResponse::from)
                .collect(Collectors.toList());
    }

    public OrderInfoResponse getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        return OrderInfoResponse.from(order);
    }
}
