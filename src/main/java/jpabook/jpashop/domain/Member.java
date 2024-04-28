package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // 테이블 명
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 하나의 회원이 여러 상품 주문
    @OneToMany(mappedBy = "member") // Order 테이블에 있는 member 필드에 매핑된!!그야 값을 변경한다해서 order의 fk 값이 변경되고 하지 않는다.
    private List<Order> orders = new ArrayList<>();
}
