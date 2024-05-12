package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // createOrderItem 이외에 객체 생성 하지 못하도록 막는다.
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

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count ) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count); // 주문수량 만큼 늘려주기
    }

    //==조회 로직==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}