package jpabook.jpashop.domain;

import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블 매핑 (중간 테이블에 있는 category_id)
    private List<Item> items = new ArrayList<>();

    // 연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 자식은 여러개의 카테고리를 가질 수 있다.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관 관계 편의 메서드==//
    public void addChildCategory(Category child) {
        // 부모, 자식 모두에게 들어가야 한다.
        this.child.add(child);
        child.setParent(this);
    }
}