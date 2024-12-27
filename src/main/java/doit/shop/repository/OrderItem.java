package doit.shop.repository;

import doit.shop.repository.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    private Integer quantity;
    private Integer orderPrice;

    @Builder
    public OrderItem(Product product, Integer quantity, Integer orderPrice){
        this.product = product;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
    }

}
