package sk.kuchta.eshop.implementation.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
