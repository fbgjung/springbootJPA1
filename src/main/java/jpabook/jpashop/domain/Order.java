package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /**
     * 회원과 주문의 관계:
     * 연관 관계의 주인을 설정해줘야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 매핑을 뭘로 할건지 fk이름이 member_id가 된다.
    private Member member; // 주문 회원;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // OrderItem에 있는 order 필드에 매핑 된 것이다.
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 주인
    // delivery 값만 세팅 해놓고, order만 persist하면 delivery가 같이 persist 호출된다. cascade
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관 관계 편의 메서드==//
    // member, order를 묶는 메서드를 만든다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    /**
     * public static void main(String[] args) {
     *         Member member = new Member();
     *         Order order = new Order();
     *
     *         // 이런식으로 적는 거 까먹을수도
     *         member.getOrders().add(order);
     *         order.setMember(member);
     *
     *         // setMember() 메서드 작성시
     *         order.setMember(member);
     *     }
     */

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}