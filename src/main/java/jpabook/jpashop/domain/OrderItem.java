package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 Item이 여러개의 orderItem에 들어갈 수 있어서?
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) // orderItem은 하나의 order만 가질 수 있다.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격 (상품가격은 바뀔 수 있다. 여기서는 주문가격)

    private int count; // 주문수량

}
