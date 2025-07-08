package sk.kuchta.eshop.api.service.specification;

import sk.kuchta.eshop.api.dto.specification.request.ProductSpecificationRequest;
import sk.kuchta.eshop.api.dto.specification.response.ProductSpecificationResponse;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;

public interface ProductSpecificationService {
    ProductSpecificationResponse save(ProductSpecificationRequest request, Product product);
    ProductSpecification findByIdInternal(long id);
    ProductSpecificationResponse findById(long id);
}
