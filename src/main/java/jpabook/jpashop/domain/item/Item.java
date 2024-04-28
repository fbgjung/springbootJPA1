package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 추상 클래스로 만든다.
 * 구현체를 가지고 할 것이기 때문에
 *
 * 상속 관계 매핑을 해야한다.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 부모 클래스에 싱글테이블 전략을 잡아준다.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
