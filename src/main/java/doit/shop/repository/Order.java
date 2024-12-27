package doit.shop.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long OrderId;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    private Integer totalPrice;
    private OrderStatus orderStatus;

    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne
    private User user;

    @Builder
    public Order(List<OrderItem> orderItems, Integer totalPrice, OrderStatus orderStatus, User user){
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.user = user;
        this.orderStatus = orderStatus;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }
}
