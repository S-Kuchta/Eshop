package sk.kuchta.eshop.implementation.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.kuchta.eshop.implementation.entity.productCategory.ProductCategory;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column
    private String description;

    @Setter
    @Column(nullable = false)
    private int price;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Setter
    private Set<ProductCategory> categories = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product")
    @Setter
    private ProductSpecification specification;

    @Column
    @Setter
    private String productCode;

    public Product(String name, String description, int price, String productCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCode = productCode;
    }

    public Product(String name, String description, int price, Set<ProductCategory> categories, ProductSpecification specification, String productCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = categories;
        this.specification = specification;
        this.productCode = productCode;
    }
}
