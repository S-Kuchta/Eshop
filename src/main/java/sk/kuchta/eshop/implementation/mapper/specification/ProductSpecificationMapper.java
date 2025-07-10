package sk.kuchta.eshop.implementation.mapper.specification;

import org.springframework.stereotype.Component;
import sk.kuchta.eshop.api.dto.specification.ProductSpecificationDTO;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;

@Component
public class ProductSpecificationMapper {



    public ProductSpecificationDTO mapToProductSpecificationDTO(ProductSpecification productSpecification) {
        return new ProductSpecificationDTO(
                productSpecification.getBrand(),
                productSpecification.getModel(),
                productSpecification.getGenericSpecificationMap()
        );
    }

    public ProductSpecification mapToProductSpecification(ProductSpecificationDTO productSpecificationDTO) {
        return new ProductSpecification(
                productSpecificationDTO.getBrand(),
                productSpecificationDTO.getModel(),
                productSpecificationDTO.getGenericSpecificationMap()
        );
    }

    public ProductSpecification mapToProductSpecification(ProductSpecificationDTO productSpecificationDTO, Product product) {
        return new ProductSpecification(
                productSpecificationDTO.getBrand(),
                productSpecificationDTO.getModel(),
                productSpecificationDTO.getGenericSpecificationMap(),
                product
        );
    }
}
