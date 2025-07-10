package sk.kuchta.eshop.implementation.mapper.product;

import org.springframework.stereotype.Component;
import sk.kuchta.eshop.api.dto.product.ProductDTO;
import sk.kuchta.eshop.api.dto.productCategories.CategoryDTO;
import sk.kuchta.eshop.api.dto.productCategories.request.ProductCategoriesRequest;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.mapper.specification.ProductSpecificationMapper;

@Component
public class ProductMapper {

    private final ProductSpecificationMapper productSpecificationMapper;

    public ProductMapper(ProductSpecificationMapper productSpecificationMapper) {
        this.productSpecificationMapper = productSpecificationMapper;
    }

    // TODO temporally new ProductCategoriesRequest - look after
    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                productSpecificationMapper.mapToProductSpecificationDTO(product.getSpecification()),
                product.getProductCode()
        );
    }

}
