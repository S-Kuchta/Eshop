package sk.kuchta.eshop.implementation.entity.productSpecification;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.kuchta.eshop.implementation.entity.product.Product;

import java.util.HashMap;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Setter
    private String brand;

    @Column
    @Setter
    private String model;

    @ElementCollection
    private Map<String, String> genericSpecificationMap = new HashMap<>();

    @OneToOne(mappedBy = "specification")
    @Setter
    private Product product;

    public ProductSpecification(String brand, String model, Map<String, String> genericSpecificationMap, Product product) {
        this.brand = brand;
        this.model = model;
        this.genericSpecificationMap = genericSpecificationMap;
        this.product = product;
    }

    public ProductSpecification(String brand, String model, Map<String, String> genericSpecificationMap) {
        this.brand = brand;
        this.model = model;
        this.genericSpecificationMap = genericSpecificationMap;
    }
}
