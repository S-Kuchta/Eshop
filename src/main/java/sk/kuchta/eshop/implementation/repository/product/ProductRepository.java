package sk.kuchta.eshop.implementation.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kuchta.eshop.implementation.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
