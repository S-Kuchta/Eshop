package sk.kuchta.eshop.api.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.api.dto.productCategories.request.ProductCategoriesRequest;
import sk.kuchta.eshop.api.dto.specification.request.ProductSpecificationRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveResponse {
    private String name;
    private String description;
    private int price;
    private ProductCategoriesRequest categoriesRequest;
    private ProductSpecificationRequest specificationRequest;
    private String productCode;
}
