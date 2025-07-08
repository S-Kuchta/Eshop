package sk.kuchta.eshop.implementation.entity.productCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.kuchta.eshop.implementation.entity.product.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parentProductCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<ProductCategory> childrenProductCategory;
}
