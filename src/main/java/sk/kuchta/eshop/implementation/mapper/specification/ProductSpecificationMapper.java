package sk.kuchta.eshop.implementation.mapper.specification;

import sk.kuchta.eshop.api.dto.specification.request.ProductSpecificationRequest;
import sk.kuchta.eshop.api.dto.specification.response.ProductSpecificationResponse;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;

public class ProductSpecificationMapper {

    public ProductSpecification mapProductSpecificationSaveRequestToProductSpecification(ProductSpecificationRequest request) {
        return new ProductSpecification(
                request.getBrand(),
                request.getModel(),
                request.getGenericSpecificationMap()
        );
    }

    public ProductSpecification mapProductSpecificationSaveRequestToProductSpecification(ProductSpecificationRequest request, Product product) {
        return new ProductSpecification(
                request.getBrand(),
                request.getModel(),
                request.getGenericSpecificationMap(),
                product
        );
    }

    public ProductSpecificationResponse mapProductSpecificationToProductSpecificationResponse(ProductSpecification productSpecification) {
        return new ProductSpecificationResponse(
                productSpecification.getBrand(),
                productSpecification.getModel(),
                productSpecification.getGenericSpecificationMap()
        );
    }
}
